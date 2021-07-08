@file:Suppress("UnstableApiUsage")

package com.artkachenko.lint_checks.detectors

import com.android.SdkConstants
import com.android.tools.lint.detector.api.*
import com.artkachenko.lint_checks.issues.HARDCODED_COLOR_ISSUE
import org.w3c.dom.Attr

class HardcodedColorDetector : ResourceXmlDetector() {

    override fun getApplicableAttributes(): Collection<String> {
        return listOf(
            SdkConstants.ATTR_COLOR,
            SdkConstants.ATTR_TEXT_COLOR,
            SdkConstants.ATTR_BACKGROUND_TINT,
            SdkConstants.ATTR_BACKGROUND,
            SdkConstants.ATTR_CARD_BACKGROUND_COLOR,
            SdkConstants.ATTR_CHIP_BACKGROUND_COLOR,
            SdkConstants.ATTR_FILL_COLOR,
            SdkConstants.ATTR_ITEM_RIPPLE_COLOR
        )
    }

    override fun visitAttribute(context: XmlContext, attribute: Attr) {
        val matchResult =
            "#([0-9a-f]{3}|[0-9a-f]{6}|[0-9a-f]{8})".toRegex().matchEntire(attribute.value)
                ?: return


        val matched = matchResult.destructured

        if (!matched.toList().isNullOrEmpty()) {
            context.report(
                HARDCODED_COLOR_ISSUE,
                context.getLocation(attribute),
                HARDCODED_COLOR_ISSUE.getExplanation(TextFormat.TEXT)
            )
        }
    }
}