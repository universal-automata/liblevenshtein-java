/*global
    $,
    window,
 */

/*jslint
    browser: true,
    multivar: true,
    this: true,
 */

$(function ($) {
    "use strict";

    var selectText = function () {
        if (window.getSelection) {
            return function () {
                var range, selection;
                selection = window.getSelection();
                range = document.createRange();
                range.selectNodeContents(this);
                selection.removeAllRanges();
                selection.addRange(range);
            };
        }

        if (document.body.createTextRange) {
            return function () {
                var range;
                range = document.body.createTextRange();
                range.moveToElementText(this);
                range.select();
            };
        }

        return $.noop;
    };

    $('span.highlight-click').on('click', selectText());
});

// vim: set ft=javascript ts=4 sw=4 et sta:
