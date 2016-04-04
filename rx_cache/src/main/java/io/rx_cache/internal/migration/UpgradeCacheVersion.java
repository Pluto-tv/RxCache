/*
 * Copyright 2016 Victor Albertos
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.rx_cache.internal.migration;


import java.util.List;

import javax.inject.Inject;

import io.rx_cache.Migration;
import io.rx_cache.internal.Persistence;
import rx.Observable;

final class UpgradeCacheVersion extends CacheVersion {
    private List<Migration> migrations;

    @Inject public UpgradeCacheVersion(Persistence persistence) {
        super(persistence);
    }

    UpgradeCacheVersion with(List<Migration> migrations) {
        this.migrations = migrations;
        return this;
    }

    Observable<Void> react() {
        if (migrations.isEmpty()) return Observable.empty();

        Migration migration = migrations.get(migrations.size() - 1);
        persistence.save(KEY_CACHE_VERSION, migration.version());

        return Observable.empty();
    }
}
