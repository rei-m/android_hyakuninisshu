///*
// * Copyright (c) 2017. Rei Matsushita
// *
// * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in
// * compliance with the License. You may obtain a copy of the License at
// *
// * http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software distributed under the License is
// * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See
// * the License for the specific language governing permissions and limitations under the License.
// */
//
//package me.rei_m.hyakuninisshu.domain.model.karuta;
//
//import org.junit.Before;
//import org.junit.Test;
//
//import static org.hamcrest.Matchers.is;
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNotEquals;
//import static org.junit.Assert.assertThat;
//
//public class KarutaIdentifierTest {
//
//    private KarutaIdentifier karutaIdentifier;
//
//    @Before
//    public void setUp() throws Exception {
//        karutaIdentifier = new KarutaIdentifier(1);
//    }
//
//    @Test
//    public void value() throws Exception {
//        assertThat(karutaIdentifier.value(), is(1));
//    }
//
//    @Test
//    public void position() throws Exception {
//        assertThat(karutaIdentifier.position(), is(0));
//    }
//
//    @Test
//    public void equals() throws Exception {
//        assertEquals(karutaIdentifier, new KarutaIdentifier(1));
//    }
//
//    @Test
//    public void notEquals() throws Exception {
//        assertNotEquals(karutaIdentifier, new KarutaIdentifier(2));
//    }
//}
