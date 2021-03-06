/* Copyright © 2015 Matthew Champion
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:
 * Redistributions of source code must retain the above copyright
      notice, this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
      notice, this list of conditions and the following disclaimer in the
      documentation and/or other materials provided with the distribution.
 * Neither the name of mattunderscore.com nor the
      names of its contributors may be used to endorse or promote products
      derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL MATTHEW CHAMPION BE LIABLE FOR ANY
DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. */

package com.mattunderscore.trees.impl.providers.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

import com.mattunderscore.trees.OperationNotSupportedForType;
import com.mattunderscore.trees.spi.SPIComponent;

/**
 * Abstract Provider implementation for {@link SPIComponent}s that constructs components using the {@link ServiceLoader}.
 * @author Matt Champion on 24/07/2015
 */
public abstract class AbstractServiceLoaderProvider<C extends SPIComponent> {
    protected final Map<Class<?>, C> componentMap = new HashMap<>();
    private final KeyMappingProvider keyMappingProvider;
    private final Class<C> componentClass;

    public AbstractServiceLoaderProvider(KeyMappingProvider keyMappingProvider, Class<C> componentClass) {
        this.keyMappingProvider = keyMappingProvider;
        this.componentClass = componentClass;
        final ServiceLoader<C> loader = ServiceLoader.load(componentClass);
        for (final C component : loader) {
            componentMap.put(component.forClass(), component);
        }
    }

    /**
     * Lookup the component. If the component is not found delegates to onNoComponent.
     * @param rawClass The component key
     * @return The value to return
     * @throws OperationNotSupportedForType Dependent on onNoComponent
     */
    protected final C getRaw(Class<?> rawClass) {
        final Class<?> mappedClass = keyMappingProvider.get(rawClass);
        final C component = componentMap.get(mappedClass);
        if (component == null) {
            return onNoComponent(rawClass);
        }
        return component;
    }

    /**
     * Provides the chance to return an object if one is not found. Intended to be overridden.
     * @param rawClass The component key not found
     * @return The value to return
     * @throws OperationNotSupportedForType The default behaviour
     */
    protected C onNoComponent(Class<?> rawClass) {
        throw new OperationNotSupportedForType(rawClass, componentClass);
    }
}
