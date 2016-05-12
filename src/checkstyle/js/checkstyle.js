$(function ($) {
  "use strict";

  $.fn.selectOnClick = function () {
    return $(this).on('click', function (event) {
      var range, selection;

      if (window.getSelection) {
        selection = window.getSelection();
        range = document.createRange();
        range.selectNodeContents(this);
        selection.removeAllRanges();
        selection.addRange(range);
      } else if (document.body.createTextRange) {
        range = document.body.createTextRange();
        range.moveToElementText(this);
        range.select();
      }
    });
  };

  $('.highlight-click').selectOnClick();
});
