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
package jhc.redsniff.webdriver.table.matchers;

import jhc.redsniff.html.tables.Table;
import jhc.redsniff.internal.matchers.CheckAndDiagnoseTogetherMatcher;
import jhc.redsniff.internal.matchers.MatcherUtil;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;

public class IsTableWithNumberOfRows extends CheckAndDiagnoseTogetherMatcher<Table> {

    Matcher<Integer> numberOfRowsMatcher;
     
    public IsTableWithNumberOfRows(Matcher<Integer> numberOfRowsMatcher) {
        this.numberOfRowsMatcher = numberOfRowsMatcher;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText(" table with ").appendDescriptionOf(numberOfRowsMatcher).appendText(" rows");
    }

    @Override
    protected boolean matchesSafely(Table item, Description mismatchDescription) {
       return  MatcherUtil.matchAndDiagnose(numberOfRowsMatcher, item.dataRows().size(), mismatchDescription);
    }

    @Factory
    public static Matcher<Table> hasNumberOfDataRows(Matcher<Integer> numberOfRowsMatcher){
        return new IsTableWithNumberOfRows(numberOfRowsMatcher);
    }
    
    @Factory
    public static Matcher<Table> hasNumberOfDataRows(int n){
        return new IsTableWithNumberOfRows(Matchers.equalTo(n));
    }
}
