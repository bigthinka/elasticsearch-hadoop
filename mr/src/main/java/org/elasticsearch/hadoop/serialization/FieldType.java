/*
 * Licensed to Elasticsearch under one or more contributor
 * license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright
 * ownership. Elasticsearch licenses this file to you under
 * the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.elasticsearch.hadoop.serialization;

import java.util.EnumSet;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;

public enum FieldType {
    // core Types
    NULL,
    BOOLEAN,
    BYTE,
    SHORT,
    INTEGER,
    LONG,
    FLOAT,
    DOUBLE,
    STRING,
    DATE,
    BINARY,
    TOKEN_COUNT,

    // compound types
    OBJECT,
    MULTI_FIELD,

    NESTED,

    // not supported yet
    IP,

    GEO_POINT,
    GEO_SHAPE,
    POINT,
    LINESTRING,
    POLYGON,
    MULTIPOINT,
    MULTIPOLYGON,
    ENVELOPE,

    // ignored
    COMPLETION;

    private static final Set<String> KNOWN_TYPES = new LinkedHashSet<String>();

    static {
        for (FieldType fieldType : EnumSet.allOf(FieldType.class)) {
            KNOWN_TYPES.add(fieldType.name());
        }
    }

    public static FieldType parse(String name) {
        String n = (name != null ? name.toUpperCase(Locale.ENGLISH) : name);
        return (KNOWN_TYPES.contains(n) ? FieldType.valueOf(n) : null);
    }

    public static boolean isRelevant(FieldType fieldType) {
        if (fieldType == null || COMPLETION == fieldType) {
            return false;
        }

        // types without a special hadoop type - they'll get translated to a string
        if (IP == fieldType ||
                GEO_POINT == fieldType || GEO_SHAPE == fieldType ||
                POINT == fieldType || LINESTRING == fieldType || POLYGON == fieldType ||
                MULTIPOINT == fieldType || MULTIPOLYGON == fieldType || ENVELOPE == fieldType) {
            return true;
        }

        //        if (NESTED == fieldType) {
        //            throw new UnsupportedOperationException("Nested fields not supported yet...");
        //        }

        return true;
    }
}