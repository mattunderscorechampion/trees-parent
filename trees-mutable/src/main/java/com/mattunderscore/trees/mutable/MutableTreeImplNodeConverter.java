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

package com.mattunderscore.trees.mutable;

import java.util.Iterator;

import com.mattunderscore.trees.construction.TopDownTreeRootBuilder;
import com.mattunderscore.trees.construction.TreeBuilderFactory;
import com.mattunderscore.trees.impl.SPISupport;
import com.mattunderscore.trees.impl.SPISupportAwareComponent;
import com.mattunderscore.trees.impl.TreeBuilderFactoryImpl;
import com.mattunderscore.trees.spi.NodeToTreeConverter;
import com.mattunderscore.trees.tree.OpenNode;

/**
 * Implementation for converting a node to a tree by copying the subtree.
 *
 * @author Matt Champion on 24/06/15.
 */
public final class MutableTreeImplNodeConverter<E, S extends OpenNode<E, S>> implements NodeToTreeConverter<E, MutableSettableNode<E>, MutableTree<E, MutableSettableNode<E>>, S>, SPISupportAwareComponent {
    private volatile TreeBuilderFactory treeBuilderFactory;

    public MutableTreeImplNodeConverter() {
    }

    @Override
    public MutableTreeImpl<E> treeFromRootNode(S node) {
        final TopDownTreeRootBuilder<E, MutableSettableNode<E>> topDownTreeRootBuilder = treeBuilderFactory.topDownBuilder();
        final TopDownTreeRootBuilder.TopDownTreeBuilder<E, MutableSettableNode<E>> treeBuilder = topDownTreeRootBuilder.root(node.getElement());

        copyChildren(treeBuilder, node);
        return treeBuilder.build(MutableTreeImpl.<E>typeKey());
    }

    private void copyChildren(TopDownTreeRootBuilder.TopDownTreeBuilderAppender<E> appender, S node) {
        final Iterator<? extends S> iterator = node.childIterator();
        while (iterator.hasNext()) {
            final S child = iterator.next();
            final TopDownTreeRootBuilder.TopDownTreeBuilderAppender<E> newAppender = appender.addChild(child.getElement());
            copyChildren(newAppender, child);
        }
    }

    @Override
    public Class<? extends MutableSettableNode> forClass() {
        return MutableTreeImpl.class;
    }

    @Override
    public void setSupport(SPISupport support) {
        treeBuilderFactory = new TreeBuilderFactoryImpl(support);
    }
}
