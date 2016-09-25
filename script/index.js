'use strict'

const client = require('cheerio-httpcli');
const fs = require('fs');

class Karuta {
  constructor(id, creator, kanaList, kanjiList, translation) {
    this.id = Number.parseInt(id);
    this.creator = creator;
    this.first_kana = kanaList[0];
    this.first_kanji = kanjiList[0];
    this.second_kana = kanaList[1];
    this.second_kanji = kanjiList[1];
    this.third_kana = kanaList[2];
    this.third_kanji = kanjiList[2];
    this.fourth_kana = kanaList[3];
    this.fourth_kanji = kanjiList[3];
    this.fifth_kana = kanaList[4];
    this.fifth_kanji = kanjiList[4];
    this.translation = translation;
    this.image_no = id;
    this.kimariji = 0;
  }

  isCollectData() {
    if (this.id === 0,
        this.checkInvalidString(this.creator) ||
        this.checkInvalidString(this.first_kana) || this.checkInvalidString(this.first_kanji) ||
        this.checkInvalidString(this.second_kana) || this.checkInvalidString(this.second_kanji) ||
        this.checkInvalidString(this.third_kana) || this.checkInvalidString(this.third_kanji) ||
        this.checkInvalidString(this.fourth_kana) || this.checkInvalidString(this.fourth_kanji) ||
        this.checkInvalidString(this.fifth_kana) || this.checkInvalidString(this.fifth_kanji) ||
        this.checkInvalidString(this.translation) ||
        this.kimariji === 0) {
        return false;
      }

    return true;
  }

  checkInvalidString(str) {
    return (str === undefined || str.length === 0);
  }
}

function getHyakuninIsshu(URL, pageCount, karutaList) {
  return new Promise(function (resolve, reject) {
    client.fetch(URL, {}, function (err, $, res) {

      const maxId = 25 * pageCount;

      const $tables = $('TABLE');
      const tableCount = $tables.length;

      for (let i=4;i<=tableCount; i+=5) {
        let totalIndex = i;

        const creatorLine = $tables.eq(totalIndex).find('TD').text().split('\n').join('').split('　');

        totalIndex+= 2;

        const textLines = $tables.eq(totalIndex).find('TD').text().split('(');

        totalIndex+= 2;

        const translationLine = $tables.eq(totalIndex).find('TD').text().split('\n').join('');

        totalIndex++;

        const id = creatorLine[0];
        const creator = creatorLine[1];

        const kanji = textLines[0].replace('\r', '').replace('\n', '');
        const kana = (id === '063') ? textLines[1] : textLines[1].split(' ').join('');

        const karuta = new Karuta(id, creator, kana.slice(0, kana.length - 1).split(/　| /), kanji.split(/　| /), translationLine);

        karutaList.push(karuta);

        if (karuta.id === maxId) {
          break;
        }
      }
      resolve(karutaList);
    });
  });
}

function getKimariji(URL, karutaList) {
  return new Promise(function(resolve, reject) {
    client.fetch(URL, {}, function (err, $, res) {

      const $tables = $('TABLE').eq(2).find("TABLE");
      const tableCount = $tables.length;

      let kimariji = 0;

      const kimarijiMap = {};

      $tables.each(function() {
        const $tds = $(this).find("TD");
        if(!isNaN($tds.eq(0).text())) {
          const karutaNo = Number.parseInt($tds.eq(0).text());
          switch (karutaNo){
            case 70:
              kimariji = 1;
              break;
            case 52:
              kimariji = 2;
              break;
            case 79:
              kimariji = 3;
              break;
            case 29:
              kimariji = 4;
              break;
            case 93:
              kimariji = 5;
              break;
            case 31:
              kimariji = 6;
              break;
          }
          kimarijiMap[karutaNo] = kimariji;
        }
      });

      karutaList.forEach(function(karuta){
        karuta.kimariji = kimarijiMap[karuta.id];
      });

      resolve(karutaList);
    });
  });
}

Promise.resolve().then(function(){
  return getHyakuninIsshu('http://www.hyakunin.stardust31.com/gendaiyaku.html', 1, []);
}).then(function(karutaList){
  return getHyakuninIsshu('http://www.hyakunin.stardust31.com/yaku-itiran.html', 2, karutaList);
}).then(function(karutaList){
  return getHyakuninIsshu('http://www.hyakunin.stardust31.com/gendaiyaku-itiran.html', 3, karutaList);
}).then(function(karutaList){
  return getHyakuninIsshu('http://www.hyakunin.stardust31.com/yaku.html', 4, karutaList);
}).then(function(karutaList){
  return getKimariji('http://www.hyakunin.stardust31.com/kimariji.html', karutaList);
}).then(function(karutaList){

  karutaList = karutaList.sort(function(a, b){
    return a.id - b.id;
  });

  const errList = [];

  karutaList.forEach(function(karuta) {
    if (!karuta.isCollectData()) {
      errList.push(karuta);
    }
  });

  if (0 < errList.length) {
    console.error(errList);
  }

  const karutaListMap = {'karuta_list': karutaList};

  fs.writeFile('../app/src/main/assets/karuta_list.json', JSON.stringify(karutaListMap), 'utf-8', function (err) {
    if (err != null) {
      console.info('エラーがあります : ' + err);
    } else {
      console.info('書き込み終了');
    }
  });
});
