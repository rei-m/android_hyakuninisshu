# CLAUDE.md

このファイルは、このリポジトリのコードを扱う際のClaude Code (claude.ai/code) へのガイダンスを提供します。

## プロジェクト概要

これは百人一首を学習するためのAndroidアプリケーションです。百人一首は100首の和歌を集めた古典的な日本の詩集です。このアプリはKotlinで構築され、MVVM + Fluxアーキテクチャパターンに従っています。

## ビルドと開発コマンド

### プロジェクトのビルド
```bash
# プロジェクト全体のビルド
./gradlew build

# デバッグ版のビルド
./gradlew assembleDebug

# リリース版のビルド
./gradlew assembleRelease

# ビルド成果物のクリーン
./gradlew clean
```

### テストの実行
```bash
# 全てのユニットテストを実行
./gradlew test

# 特定のモジュールのユニットテストを実行
./gradlew :module_name:test
# 例: ./gradlew :domain:test

# Androidインストルメンテーションテストを実行
./gradlew connectedAndroidTest
```

### コード品質
```bash
# ktlintでコードスタイルをチェック
./gradlew ktlintCheck

# ktlintで自動フォーマット
./gradlew ktlintFormat

# lintチェックを実行
./gradlew lint
```

## アーキテクチャ概要

このアプリケーションは関心の分離を明確にしたマルチモジュールアーキテクチャに従っています：

### モジュール構造
- **app**: Applicationクラス、MainActivity、Daggerセットアップを含むメインアプリケーションモジュール
- **domain**: ビジネスロジックとドメインモデル（Karuta、Question、Examエンティティ）
- **infrastructure**: Roomデータベースとリポジトリを使用したデータレイヤーの実装
- **state**: アクション、ストア、ディスパッチャーを使用したFlux/Redux形式の状態管理
- **feature/***: 画面や機能を表すUIフィーチャーモジュール：
  - `corecomponent`: 共有UIコンポーネントとリソース
  - `splash`, `question`, `material`, `exam*`, `training*`: 機能固有の画面

### アーキテクチャパターン
アプリはMVVM + Fluxアーキテクチャを使用：
1. **Actions**: ユーザーインタラクションがActionCreatorを通じてActionsをトリガー
2. **Dispatcher**: ActionsがRxJava経由でStoresにディスパッチされる
3. **Stores**: Actionsを処理し、状態変更を発行
4. **ViewModels**: Storesをサブスクライブし、LiveDataをViewsに公開
5. **Views**: FragmentsがViewModelsを観察し、UIを更新

### 主要技術
- **依存性注入**: component/subcomponent階層を持つDagger 2
- **リアクティブプログラミング**: 非同期操作とイベントストリーム用のRxJava 2
- **データベース**: ローカルデータ永続化のためのRoom
- **ナビゲーション**: SafeArgsを使用したAndroid Navigation Component
- **ビューバインディング**: UIコンポーネント用のData Binding
- **画像読み込み**: かるたカード画像用のGlide

### データフローの例
```
ユーザーアクション → Fragment → ViewModel → ActionCreator → Action → Dispatcher → Store → ViewModel → Fragment
```

## 重要な考慮事項

1. **モジュール化**: 各機能は独自のモジュールにあります。新機能追加時は既存のモジュール構造に従ってください
2. **状態管理**: すべての状態変更はFluxパターンを通じて行われます - 状態を直接変更しないでください
3. **リソース**: かるた画像は `state/src/main/res/drawable-*dpi/` に保存されています
4. **データベース**: かるたデータは `infrastructure/src/main/assets/karuta_list_v_2.json` から読み込まれます
5. **テスト**: 各モジュールは同じパッケージ構造に従った独自のテストディレクトリを持っています
6. **ビルドバリアント**: デバッグビルドには `.debug` サフィックスが付き、Firebase Crashlyticsは無効化されます

## 開発のヒント

- データベーススキーマを変更する際は、`AppDatabase.kt` のデータベースバージョンをインクリメントしてください
- フィーチャーモジュールは共通UIコンポーネントを提供する `corecomponent` に依存しています
- 新しいViewModelを作成する際は、既存のViewModelFactoryパターンを使用してください
- Actions、Stores、Eventsの確立された命名規則に従ってください