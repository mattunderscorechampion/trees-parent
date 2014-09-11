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

package com.mattunderscore.trees.common.traversers;

import com.mattunderscore.trees.*;
import com.mattunderscore.trees.common.LinkedTree;
import com.mattunderscore.trees.common.TreesImpl;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * @author Matt Champion on 03/09/14.
 */
public final class PostOrderIteratorTest {
    @Test
    public void test0()
    {
        final Trees trees = new TreesImpl();
        final BottomUpTreeBuilder<String> builder = trees.treeBuilderFactory().bottomUpBuilder();
        final Tree<String, Node<String>> tree = builder.create("f",
            builder.create("b",
                builder.create("a"),
                builder.create("d",
                    builder.create("c"),
                    builder.create("e"))),
            builder.create("i",
                builder.create("h",
                    builder.create("g")))).build(LinkedTree.class);

        final Iterator<Node<String>> iterator = new PostOrderIterator<>(tree);
        assertEquals("a", iterator.next().getElement());
        assertEquals("c", iterator.next().getElement());
        assertEquals("e", iterator.next().getElement());
        assertEquals("d", iterator.next().getElement());
        assertEquals("b", iterator.next().getElement());
        assertEquals("g", iterator.next().getElement());
        assertEquals("h", iterator.next().getElement());
        assertEquals("i", iterator.next().getElement());
        assertEquals("f", iterator.next().getElement());
        assertFalse(iterator.hasNext());
    }
}
