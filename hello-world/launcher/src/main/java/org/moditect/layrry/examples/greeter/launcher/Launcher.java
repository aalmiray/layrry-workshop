/**
 *  Copyright 2020-2021 The ModiTect authors
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.moditect.layrry.examples.greeter.launcher;

import org.moditect.layrry.Layers;

public class Launcher {
    public static void main(String[] args) {
        Layers layers = Layers.builder()
            .layer("log")
                .withModule("org.apache.logging.log4j:log4j-api:2.13.1")
                .withModule("org.apache.logging.log4j:log4j-core:2.13.1")
                .withModule("org.moditect.layrry.examples.greeter:logconfig:1.0.0")
            .layer("foo")
                .withParent("log")
                .withModule("org.moditect.layrry.examples.greeter:greeter:1.0.0")
                .withModule("org.moditect.layrry.examples.greeter:foo:1.0.0")
            .layer("bar")
                .withParent("log")
                .withModule("org.moditect.layrry.examples.greeter:greeter:2.0.0")
                .withModule("org.moditect.layrry.examples.greeter:bar:1.0.0")
            .layer("app")
                .withParent("foo")
                .withParent("bar")
                .withModule("org.moditect.layrry.examples.greeter:app:1.0.0")
            .build();

        layers.run("org.moditect.layrry.examples.greeter.app/org.moditect.layrry.examples.greeter.app.App", args);
    }
}
