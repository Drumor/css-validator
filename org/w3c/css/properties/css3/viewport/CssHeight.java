//
// Author: Yves Lafon <ylafon@w3.org>
//
// (c) COPYRIGHT MIT, ERCIM, Keio, Beihang, 2018.
// Please first read the full copyright statement in file COPYRIGHT.html
package org.w3c.css.properties.css3.viewport;

import org.w3c.css.atrules.css.AtRuleViewport;
import org.w3c.css.parser.CssStyle;
import org.w3c.css.util.ApplContext;
import org.w3c.css.util.InvalidParamException;
import org.w3c.css.values.CssExpression;
import org.w3c.css.values.CssValue;
import org.w3c.css.values.CssValueList;

import java.util.ArrayList;

import static org.w3c.css.values.CssOperator.SPACE;

/**
 * @spec https://www.w3.org/TR/2016/WD-css-device-adapt-1-20160329/#descdef-viewport-height
 */
public class CssHeight extends org.w3c.css.properties.css.viewport.CssHeight {

    /**
     * Create a new CssHeight
     */
    public CssHeight() {
        value = initial;  // this is wrong...
    }

    /**
     * Creates a new CssHeight
     *
     * @param expression The expression for this property
     * @throws org.w3c.css.util.InvalidParamException
     *          Expressions are incorrect
     */
    public CssHeight(ApplContext ac, CssExpression expression, boolean check)
            throws InvalidParamException {
        CssValue val;
        char op;
        ArrayList<CssValue> values = new ArrayList<>();

        setByUser();

        if (expression.getCount() > 2) {
            throw new InvalidParamException("unrecognize", ac);
        }

        while (!expression.end()) {
            val = expression.getValue();
            op = expression.getOperator();
            values.add(AtRuleViewport.checkViewportLenght(val, this, ac));
            expression.next();
            if ((op != SPACE)) {
                throw new InvalidParamException("operator",
                        ((new Character(op)).toString()), ac);
            }
        }
        value = (values.size() == 1) ? values.get(0) : new CssValueList(values);
    }

    /**
     * Add this property to the CssStyle.
     *
     * @param style The CssStyle
     */
    public void addToStyle(ApplContext ac, CssStyle style) {
        super.addToStyle(ac, style);

        CssValue min, max;
        if (value instanceof CssValueList) {
            min = ((CssValueList) value).get(0);
            max = ((CssValueList) value).get(1);
        } else {
            min = value;
            max = value;
        }
        CssMinHeight newMin = new CssMinHeight();
        newMin.value = min;
        newMin.addToStyle(ac, style);

        CssMaxHeight newMax = new CssMaxHeight();
        newMax.value = max;
        newMax.addToStyle(ac, style);
    }

    public CssHeight(ApplContext ac, CssExpression expression)
            throws InvalidParamException {
        this(ac, expression, false);
    }

}

