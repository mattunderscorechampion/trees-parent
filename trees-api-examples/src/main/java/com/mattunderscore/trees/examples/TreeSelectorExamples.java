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

package com.mattunderscore.trees.examples;

import com.mattunderscore.trees.mutable.MutableNode;
import com.mattunderscore.trees.mutable.MutableNodeTree;
import com.mattunderscore.trees.selection.TreeSelector;
import com.mattunderscore.trees.tree.Node;
import com.mattunderscore.trees.tree.Tree;

import java.util.Iterator;

/**
 * @author Matt Champion on 08/08/14.
 */
public final class TreeSelectorExamples {
    public void selectorExample(TreeSelector<String> selector, Tree<String, Node<String>> tree) {
        final Iterator<Tree<String, Node<String>>> iterator = selector.select(tree);
    }

    public void selectorExampleOnMutableTree(TreeSelector<String> selector, MutableNodeTree<String, MutableNode<String>> tree) {
        final Iterator<MutableNodeTree<String, MutableNode<String>>> iterator = selector.select(tree);
    }
}
