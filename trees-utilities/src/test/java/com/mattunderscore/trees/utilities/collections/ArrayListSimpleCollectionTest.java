/* Copyright © 2014 Matthew Champion
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

package com.mattunderscore.trees.utilities.collections;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests for {@link com.mattunderscore.trees.utilities.collections.ArrayListSimpleCollection}.
 * @author Matt Champion on 19/09/14.
 */
public final class ArrayListSimpleCollectionTest {
    @Test
    public void createWithInitialValues() {
        final List<String> set = new ArrayList<>();
        set.add("a");
        set.add("b");
        final ArrayListSimpleCollection<String> collection = new ArrayListSimpleCollection<>(set);
        assertFalse(collection.isEmpty());
        assertEquals(2, collection.size());
        final Iterator<String> iterator = collection.iterator();
        assertEquals("a", iterator.next());
        assertEquals("b", iterator.next());
        assertFalse(iterator.hasNext());
        final Iterator<String> structuralIterator = collection.structuralIterator();
        assertEquals("a", structuralIterator.next());
        assertEquals("b", structuralIterator.next());
        assertFalse(structuralIterator.hasNext());
    }

    @Test
    public void add() {
        final ArrayListSimpleCollection<String> collection = new ArrayListSimpleCollection<>();
        assertTrue(collection.isEmpty());
        collection.add("a");
        assertFalse(collection.isEmpty());
        assertEquals(1, collection.size());
        assertEquals("a", collection.iterator().next());
        assertEquals("a", collection.structuralIterator().next());
    }

    @Test(expected = NullPointerException.class)
    public void addNull() {
        final ArrayListSimpleCollection<String> collection = new ArrayListSimpleCollection<>();
        collection.add(null);
    }

    @Test
    public void remove() {
        final ArrayListSimpleCollection<String> collection = new ArrayListSimpleCollection<>();
        collection.add("a");
        assertTrue(collection.remove("a"));
        assertEquals(0, collection.size());
        assertFalse(collection.iterator().hasNext());
        assertFalse(collection.structuralIterator().hasNext());
    }

    @Test
    public void removeNotPresent() {
        final ArrayListSimpleCollection<String> collection = new ArrayListSimpleCollection<>();
        collection.add("a");
        assertFalse(collection.remove("b"));
        assertEquals(1, collection.size());
        assertEquals("a", collection.iterator().next());
        assertEquals("a", collection.structuralIterator().next());
    }

    @Test
    public void removeIterator() {
        final ArrayListSimpleCollection<String> collection = new ArrayListSimpleCollection<>();
        collection.add("a");
        final Iterator<String> iterator = collection.iterator();
        iterator.next();
        iterator.remove();

        assertEquals(0, collection.size());
    }

    @Test
    public void removeStructuralIterator() {
        final ArrayListSimpleCollection<String> collection = new ArrayListSimpleCollection<>();
        collection.add("a");
        final Iterator<String> iterator = collection.structuralIterator();
        iterator.next();
        iterator.remove();

        assertEquals(0, collection.size());
    }
}
