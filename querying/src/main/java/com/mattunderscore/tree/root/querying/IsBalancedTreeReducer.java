/* Copyright © 2016 Matthew Champion
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

package com.mattunderscore.tree.root.querying;

import com.mattunderscore.tree.root.querying.IsBalancedTreeReducer.IsBalancedResult;
import com.mattunderscore.trees.binary.OpenBinaryTreeNode;
import com.mattunderscore.trees.query.ReductionResult;
import com.mattunderscore.trees.query.ReductionResults;
import com.mattunderscore.trees.utilities.ComparableComparator;

import java.util.Collection;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

/**
 * Implementation of a partial reducer that tests if a subtree is balanced.
 * @author Matt Champion on 29/04/16
 */
/*package*/ final class IsBalancedTreeReducer<E, N extends OpenBinaryTreeNode<E, N>>
        implements BiFunction<N, Collection<IsBalancedResult>, ReductionResult<IsBalancedResult>> {

    private static final IsBalancedTreeReducer REDUCER = new IsBalancedTreeReducer();

    private IsBalancedTreeReducer() {
    }

    @Override
    public ReductionResult<IsBalancedResult> apply(N node, Collection<IsBalancedResult> childResults) {
        if (node.isLeaf()) {
            return ReductionResults.result(new IsBalancedResult(true, 0));
        }

        final int minHeight = getMinHeight(childResults);
        final int maxHeight = getMaxHeight(childResults);

        final int heightDifference = maxHeight - minHeight;
        if (heightDifference > 1) {
            return ReductionResults.haltAndResult(new IsBalancedResult(false, -1));
        }

        return ReductionResults.result(new IsBalancedResult(true, maxHeight + 1));
    }

    private final int getMinHeight(Collection<IsBalancedResult> childResults) {
        if (childResults.size() < 2) {
            return 0;
        }
        else {
            return childResults
                    .stream()
                    .map(result -> result.height)
                    .collect(Collectors.minBy(ComparableComparator.get()))
                    .get();
        }
    }

    private final int getMaxHeight(Collection<IsBalancedResult> childResults) {
        if (childResults.size() == 0) {
            return 0;
        }
        else {
            return childResults
                    .stream()
                    .map(result -> result.height)
                    .collect(Collectors.maxBy(ComparableComparator.get()))
                    .get();
        }
    }

    public static <E, N extends OpenBinaryTreeNode<E, N>> BiFunction<N, Collection<IsBalancedResult>, ReductionResult<IsBalancedResult>> get() {
        return REDUCER;
    }

    public static final class IsBalancedResult {
        private final boolean isBalanced;
        private final int height;

        public IsBalancedResult(boolean isBalanced, int height) {
            this.isBalanced = isBalanced;
            this.height = height;
        }

        public boolean isBalanced() {
            return isBalanced;
        }
    }
}
