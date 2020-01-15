/*
 * WorldGuard, a suite of tools for Minecraft
 * Copyright (C) sk89q <http://www.sk89q.com>
 * Copyright (C) WorldGuard team and contributors
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package com.sk89q.worldguard.protection.regions;

import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldguard.util.Normal;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.regex.Pattern;

import static com.google.common.base.Preconditions.checkNotNull;

public final class RegionIdentifier {
    private static final Pattern VALID_NAME_PATTERN = Pattern.compile("^[A-Za-z0-9_,'\\-\\+/]{1,}$");

    private String namespace;
    private String name;

    /**
     * Construct a new RegionIdentifier for describing region name information, using the global namespace.<br>
     * Equivalent to {@link #RegionIdentifier(String, String) RegionIdentifier(null, name)}<br>
     * <code>namespace</code> will be set to null.
     *
     * @param name the name for this region
     * @throws IllegalArgumentException thrown if the name is invalid (see {@link #isValidName(String)}
     */
    public RegionIdentifier(String name) {
        this(null, name);
    }

    /**
     * Construct a new RegionIdentifier for describing region name information.
     *
     * @param namespace the namespace name for this region, or null if there isn't one
     * @param name the name for this region
     * @throws IllegalArgumentException thrown if the namespace is invalid (see {@link #isValidNamespace(String)}
     * @throws IllegalArgumentException thrown if the name is invalid (see {@link #isValidName(String)}
     */
    public RegionIdentifier(@Nullable String namespace, String name) {
        if (!isValidNamespace(name)) {
            throw new IllegalArgumentException("Invalid region namespace: " + namespace);
        }

        if (!isValidName(name)) {
            throw new IllegalArgumentException("Invalid region name: " + name);
        }

        this.namespace = namespace == null ? null : Normal.normalize(namespace);
        this.name = Normal.normalize(name);
    }

    public String getQualifiedName() {
        return getNamespace().map((ns) -> ns + ':' + getName()).orElse(getName());
    }

    public Optional<String> getNamespace() {
        return Optional.ofNullable(namespace);
    }

    public String getName() {
        return name;
    }

    public static boolean isValidNamespace(String namespace) {
        if (namespace == null) {
            return true;
        }

        return VALID_NAME_PATTERN.matcher(namespace).matches();
    }

    public static boolean isValidName(String name) {
        checkNotNull(name);
        return VALID_NAME_PATTERN.matcher(name).matches();
    }
}
