//
// Wire
// Copyright (C) 2016 Wire Swiss GmbH
//
// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program. If not, see http://www.gnu.org/licenses/.
//

package com.wire.bots.sdk;

import javax.validation.constraints.NotNull;

/**
 * Application configuration class. Extend this class to add your custom configuration
 */
public class Configuration extends io.dropwizard.Configuration {
    /**
     * Path to the directory that will hold the cryptoBox data. This directory must exist
     */
    public String cryptoDir = "crypto";

    /**
     * Authentication token. Must start with: 'Bearer'
     */
    @NotNull
    public String auth;

    public String getCryptoDir() {
        return cryptoDir;
    }

    public String[] getDirs() {
        return new String[0];
    }

    public String getAuth() {
        return auth;
    }

    public final static class ConfigValueNotFoundException extends RuntimeException {
        public ConfigValueNotFoundException(String message) {
            super(message);
        }
    }

    public void setCryptoDir(String cryptoDir) {
        this.cryptoDir = cryptoDir;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public static String propOrEnv(String prop, boolean strict) {
        final String env = prop.replace('.', '_').toUpperCase();
        final String val = System.getProperty(prop, System.getenv(env));
        if (val == null && strict) {
            throw new ConfigValueNotFoundException(prop + " (" + env + ") not found");
        }

        return val;
    }

    public static String propOrEnv(String prop, String def) {
        final String val = propOrEnv(prop, false);
        if (val == null) {
            return def;
        } else {
            return val;
        }
    }
}
