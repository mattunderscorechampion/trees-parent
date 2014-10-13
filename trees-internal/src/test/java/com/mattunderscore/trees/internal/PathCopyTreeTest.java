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

package com.mattunderscore.trees.internal;

import com.mattunderscore.trees.Trees;
import com.mattunderscore.trees.common.TreesImpl;
import com.mattunderscore.trees.construction.BottomUpTreeBuilder;
import com.mattunderscore.trees.internal.pathcopy.PathCopyTree;
import com.mattunderscore.trees.mutable.MutableNode;
import com.mattunderscore.trees.mutable.MutableTree;
import com.mattunderscore.trees.traversal.TreeIteratorFactory;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.*;

/**
 * @author Matt Champion on 13/09/14.
 */
public final class PathCopyTreeTest {
    private static TreeIteratorFactory iterators;
    private static BottomUpTreeBuilder<String> builder;

    @BeforeClass
    public static void setUp() {
        final Trees trees = new TreesImpl();
        iterators = trees.treeIterators();
        builder = trees.treeBuilders().bottomUpBuilder();
    }

    @Test
    public void test() {
        final MutableTree<String, MutableNode<String>> tree = builder.build(PathCopyTree.class);
        final Iterator<String> iterator0 = iterators.inOrderElementsIterator(tree);
        final MutableNode<String> root = tree.setRoot("root");
        assertFalse(iterator0.hasNext());
        assertEquals(0, root.getChildren().size());

        final Iterator<String> iterator1 = iterators.inOrderElementsIterator(tree);
        root.addChild("child");
        assertEquals(0, root.getChildren().size());
        assertNotEquals(root, tree.getRoot());
        assertEquals(1, tree.getRoot().getChildren().size());

        assertTrue(iterator1.hasNext());
        assertEquals("root", iterator1.next());
        assertFalse(iterator1.hasNext());

        final Iterator<String> iterator2 = iterators.inOrderElementsIterator(tree);
        final MutableNode<String> newRoot = tree.setRoot("newRoot");

        assertTrue(iterator2.hasNext());
        assertEquals("child", iterator2.next());
        assertEquals("root", iterator2.next());
        assertFalse(iterator2.hasNext());

        final MutableNode<String> newChild = newRoot.addChild("newChild");
        final Iterator<String> iterator3 = iterators.inOrderElementsIterator(tree);
        assertTrue(tree.getRoot().removeChild(newChild));
        final Iterator<String> iterator4 = iterators.inOrderElementsIterator(tree);

        assertTrue(iterator3.hasNext());
        assertEquals("newChild", iterator3.next());
        assertEquals("newRoot", iterator3.next());
        assertFalse(iterator3.hasNext());
        assertTrue(iterator4.hasNext());
        assertEquals("newRoot", iterator4.next());
        assertFalse(iterator4.hasNext());

        assertFalse(newRoot.removeChild(root));
    }
}
