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

package com.mattunderscore.trees.pathcopy.holder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.mattunderscore.trees.Trees;
import com.mattunderscore.trees.impl.TreesImpl;
import com.mattunderscore.trees.linked.tree.LinkedTree;
import com.mattunderscore.trees.tree.Node;
import com.mattunderscore.trees.tree.Tree;

/**
 * Test for {@link Converter}.
 * @author Matt Champion on 07/07/2015.
 */
public final class ConverterTest {

    @Test
    public void key() {
        final Converter<String> converter = new Converter<>();
        assertEquals(PathCopyTree.class, converter.forClass());
    }

    @Test
    public void build() {
        final Tree<String, Node<String>> tree = getTree();
        final Converter<String> converter = new Converter<>();
        final PathCopyTree<String> pathCopyTree = converter.build(tree);
        assertEquals("root", pathCopyTree.getRoot().getElement());
        assertTrue(pathCopyTree.getRoot().isLeaf());
        assertFalse(pathCopyTree.isEmpty());
    }

    @SuppressWarnings("unchecked")
    private Tree<String, Node<String>> getTree() {
        final Trees trees = new TreesImpl();
        return trees.treeBuilders().bottomUpBuilder().create("root").build(LinkedTree.class);
    }
}