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

package com.mattunderscore.trees.base;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.mattunderscore.trees.mutable.MutableSettableStructuredNode;
import com.mattunderscore.trees.tree.OpenNode;
import com.mattunderscore.simple.collections.DuplicateOnWriteSimpleCollection;

import org.junit.Test;

/**
 * Unit tests for ImmutableNode.
 * @author Matt Champion on 20/12/14
 */
public final class ImmutableNodeTest {
    @Test
    public void test0() {
        final OpenNode<String, MutableSettableStructuredNode<String>> node =
            new ImmutableNode<String, MutableSettableStructuredNode<String>>("a", new Object[0]) {};
        assertEquals("a", node.getElement());
        assertEquals(String.class, node.getElementClass());
        assertTrue(node.isLeaf());
    }

    @Test
    public void test1() {
        final OpenNode<String, MutableSettableStructuredNode<String>> node =
            new TestImmutableNode();
        assertEquals("a", node.getElement());
        assertEquals(String.class, node.getElementClass());
        assertTrue(node.isLeaf());
    }

    @Test
    public void test2() {
        final OpenNode<String, MutableSettableStructuredNode<String>> node =
            new ImmutableNode<String, MutableSettableStructuredNode<String>>(
                "a",
                DuplicateOnWriteSimpleCollection.create()) {};
        assertEquals("a", node.getElement());
        assertEquals(String.class, node.getElementClass());
        assertTrue(node.isLeaf());
    }

    private static class TestImmutableNode extends ImmutableNode<String, MutableSettableStructuredNode<String>> {
        public TestImmutableNode() {
            super("a", new TestImmutableNode[0]);
        }
    }
}
