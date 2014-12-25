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

package com.mattunderscore.trees.common.matchers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.mattunderscore.trees.base.ImmutableNode;
import com.mattunderscore.trees.selection.NodeMatcher;
import com.mattunderscore.trees.tree.Node;
import org.junit.Test;

/**
 * Unit tests for ConjunctionMatcher.
 * @author Matt Champion on 25/12/14
 */
public final class ConjunctionMatcherTest {

    @Test
    public void simplifyingCreator0() {
        final NodeMatcher<String> matcher0 = new EqualityMatcher<>("a");
        final NodeMatcher<String> matcher1 = new EqualityMatcher<>("a");
        final NodeMatcher<String> matcher = ConjunctionMatcher.create(matcher0, matcher1);

        assertEquals(matcher, matcher0);
    }

    @Test
    public void simplifyingCreator1() {
        final NodeMatcher<String> matcher0 = new AlwaysMatcher<>();
        final NodeMatcher<String> matcher1 = new EqualityMatcher<>("a");
        final NodeMatcher<String> matcher = ConjunctionMatcher.create(matcher0, matcher1);

        assertEquals(matcher, matcher1);
    }

    @Test
    public void simplifyingCreator2() {
        final NodeMatcher<String> matcher0 = new EqualityMatcher<>("a");
        final NodeMatcher<String> matcher1 = new AlwaysMatcher<>();
        final NodeMatcher<String> matcher = ConjunctionMatcher.create(matcher0, matcher1);

        assertEquals(matcher, matcher0);
    }

    @Test
    public void simplifyingCreator3() {
        final NodeMatcher<String> matcher0 = new NeverMatcher<>();
        final NodeMatcher<String> matcher1 = new EqualityMatcher<>("a");
        final NodeMatcher<String> matcher = ConjunctionMatcher.create(matcher0, matcher1);

        assertEquals(matcher, matcher0);
    }

    @Test
    public void simplifyingCreator4() {
        final NodeMatcher<String> matcher0 = new EqualityMatcher<>("a");
        final NodeMatcher<String> matcher1 = new NeverMatcher<>();
        final NodeMatcher<String> matcher = ConjunctionMatcher.create(matcher0, matcher1);

        assertEquals(matcher, matcher1);
    }

    @Test
    public void matches() {
        final Node<String> node = new ImmutableNode<String>("a", new Object[0]) {};
        final NodeMatcher<String> matcher0 = new EqualityMatcher<>("a");
        final NodeMatcher<String> matcher1 = new EqualityMatcher<>("a");
        final NodeMatcher<String> matcher = new ConjunctionMatcher<>(matcher0, matcher1);

        assertTrue(matcher.matches(node));
    }

    @Test
    public void leftMatches() {
        final Node<String> node = new ImmutableNode<String>("a", new Object[0]) {};
        final NodeMatcher<String> matcher0 = new EqualityMatcher<>("a");
        final NodeMatcher<String> matcher1 = new NeverMatcher<>();
        final NodeMatcher<String> matcher = new ConjunctionMatcher<>(matcher0, matcher1);

        assertFalse(matcher.matches(node));
    }

    @Test
    public void rightMatches() {
        final Node<String> node = new ImmutableNode<String>("a", new Object[0]) {};
        final NodeMatcher<String> matcher0 = new NeverMatcher<>();
        final NodeMatcher<String> matcher1 = new EqualityMatcher<>("a");
        final NodeMatcher<String> matcher = new ConjunctionMatcher<>(matcher0, matcher1);

        assertFalse(matcher.matches(node));
    }

    @Test
    public void neitherMatches() {
        final Node<String> node = new ImmutableNode<String>("a", new Object[0]) {};
        final NodeMatcher<String> matcher0 = new NeverMatcher<>();
        final NodeMatcher<String> matcher1 = new EqualityMatcher<>("b");
        final NodeMatcher<String> matcher = new ConjunctionMatcher<>(matcher0, matcher1);

        assertFalse(matcher.matches(node));
    }

    @Test
    public void testEquals0() {
        final NodeMatcher<String> matcher00 = new NeverMatcher<>();
        final NodeMatcher<String> matcher01 = new EqualityMatcher<>("a");
        final NodeMatcher<String> matcher0 = new ConjunctionMatcher<>(matcher00, matcher01);

        final NodeMatcher<String> matcher10 = new NeverMatcher<>();
        final NodeMatcher<String> matcher11 = new EqualityMatcher<>("a");
        final NodeMatcher<String> matcher1 = new ConjunctionMatcher<>(matcher10, matcher11);

        assertTrue(matcher0.equals(matcher1));
        assertTrue(matcher1.equals(matcher0));
        assertEquals(matcher0.hashCode(), matcher1.hashCode());
    }

    @Test
    public void testEquals1() {
        final NodeMatcher<String> matcher00 = new NeverMatcher<>();
        final NodeMatcher<String> matcher01 = new EqualityMatcher<>("a");
        final NodeMatcher<String> matcher0 = new ConjunctionMatcher<>(matcher00, matcher01);

        final NodeMatcher<String> matcher10 = new EqualityMatcher<>("a");
        final NodeMatcher<String> matcher11 = new NeverMatcher<>();
        final NodeMatcher<String> matcher1 = new ConjunctionMatcher<>(matcher10, matcher11);

        assertTrue(matcher0.equals(matcher1));
        assertTrue(matcher1.equals(matcher0));
        assertEquals(matcher0.hashCode(), matcher1.hashCode());
    }

    @Test
    public void testNotEquals0() {
        final NodeMatcher<String> matcher00 = new NeverMatcher<>();
        final NodeMatcher<String> matcher01 = new EqualityMatcher<>("a");
        final NodeMatcher<String> matcher0 = new ConjunctionMatcher<>(matcher00, matcher01);

        final NodeMatcher<String> matcher10 = new NeverMatcher<>();
        final NodeMatcher<String> matcher11 = new EqualityMatcher<>("b");
        final NodeMatcher<String> matcher1 = new ConjunctionMatcher<>(matcher10, matcher11);

        assertFalse(matcher0.equals(matcher1));
    }

    @Test
    public void testNotEquals1() {
        final NodeMatcher<String> matcher0 = new NeverMatcher<>();
        final NodeMatcher<String> matcher1 = new EqualityMatcher<>("b");
        final NodeMatcher<String> matcher = new ConjunctionMatcher<>(matcher0, matcher1);

        assertFalse(matcher.equals(null));
    }

    @Test
    public void testNotEquals2() {
        final NodeMatcher<String> matcher0 = new NeverMatcher<>();
        final NodeMatcher<String> matcher1 = new EqualityMatcher<>("b");
        final NodeMatcher<String> matcher = new ConjunctionMatcher<>(matcher0, matcher1);

        assertFalse(matcher.equals(new Object()));
    }
}
