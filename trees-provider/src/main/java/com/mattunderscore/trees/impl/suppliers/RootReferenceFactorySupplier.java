package com.mattunderscore.trees.impl.suppliers;

import com.mattunderscore.trees.binary.OpenMutableBinaryTreeNode;
import com.mattunderscore.trees.spi.RootReferenceFactory;

/**
 * Supplier for {@link RootReferenceFactory} components.
 * @author Matt Champion on 26/08/2015
 */
public interface RootReferenceFactorySupplier {
    /**
     * Get a {@link RootReferenceFactory}.
     * @param node The node to get a reference factory for
     * @param <E> The element type
     * @param <N> The node type
     * @return The factory
     */
    <E, N extends OpenMutableBinaryTreeNode<E, N>> RootReferenceFactory<E, N> get(N node);
}
