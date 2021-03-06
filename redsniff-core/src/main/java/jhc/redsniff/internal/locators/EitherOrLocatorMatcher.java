/*******************************************************************************
 * Copyright 2014 JHC Systems Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package jhc.redsniff.internal.locators;

import static jhc.redsniff.internal.core.CollectionOf.collectionOf;

import java.util.ArrayList;

import jhc.redsniff.internal.core.CollectionOf;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;

public class EitherOrLocatorMatcher<E, C> extends TypeSafeDiagnosingMatcher<E> implements MatcherLocator<E, C> {

    private final MatcherLocator<E, C> locatorMatcherA;
    private final MatcherLocator<E, C> locatorMatcherB;

    public EitherOrLocatorMatcher(MatcherLocator<E, C> locatorMatcherA, MatcherLocator<E, C> locatorMatcherB) {
        this.locatorMatcherA = locatorMatcherA;
        this.locatorMatcherB = locatorMatcherB;
    }

    @Override
    public void describeTo(Description description) {
        description
                .appendDescriptionOf(locatorMatcherA)
                .appendText(" or ")
                .appendDescriptionOf(locatorMatcherB);
    }

    @Override
    public CollectionOf<E> findElementsFrom(C context) {
        CollectionOf<E> combinedElements = collectionOf(new ArrayList<E>());
        combinedElements.addAll(locatorMatcherA.findElementsFrom(context));
        combinedElements.addAll(locatorMatcherB.findElementsFrom(context));
        return combinedElements;
    }

    @Override
    public boolean canBehaveAsLocator() {
        return locatorMatcherA.canBehaveAsLocator() && locatorMatcherB.canBehaveAsLocator();
    }

    @Override
    public String nameOfAttributeUsed() {
        String attributeA = locatorMatcherA.nameOfAttributeUsed();
        String attributeB = locatorMatcherB.nameOfAttributeUsed();
        if (attributeA.equals(attributeB))
            return attributeA;
        else
            return attributeA + "_OR_" + attributeB;
    }

    @Override
    protected boolean matchesSafely(E actual, Description mismatchDescription) {
        boolean matches = locatorMatcherA.matches(actual) || locatorMatcherB.matches(actual);
        if (!matches) {
            locatorMatcherA.describeMismatch(actual, mismatchDescription);
            mismatchDescription.appendText(" and ");
            locatorMatcherB.describeMismatch(actual, mismatchDescription);
        }
        return matches;
    }

    public static <E, C> MatcherLocator<E, C> eitherOf(
            MatcherLocator<E, C> locatorMatcherA,
            MatcherLocator<E, C> locatorMatcherB) {
        return new EitherOrLocatorMatcher<E, C>(locatorMatcherA, locatorMatcherB);
    }

    @Override
    public void describeLocatorTo(Description description) {
       locatorMatcherA.describeLocatorTo(description);
       description.appendText("\\");
       locatorMatcherB.describeLocatorTo(description);
    }

	@Override
	public int specifity() {
		return Math.max(locatorMatcherA.specifity(), locatorMatcherB.specifity());//TODO - check ok
	}
}
