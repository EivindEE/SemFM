/**
 * thuneScroller - jQuery plugin for scrolling through an ul in a carousel format
 * @requires jQuery v1.2 or above
 *
 * http://gmarwaha.com/jquery/jcarousellite/
 *
 * Copyright (c) 2011 Torstein Thune (thunemedia.no)
 * Based on jCarouselLite by Ganeshji Marwaha (gmarwaha.com)
 * Dual licensed under the MIT and GPL licenses:
 * http://www.opensource.org/licenses/mit-license.php
 * http://www.gnu.org/licenses/gpl.html
 *
 * Version: 0.5.5
 * Note: buggy animations + much horrible code (to be fixed)
 */

(function($) {                                          // Compliant with jquery.noConflict()
$.fn.thuneScroller = function(o) {
    o = $.extend({
        btnPrev: null,
        btnNext: null,

        speed: 200,

        visible: 2,
        start: 0,
        scroll:3,

        beforeStart: null,
        afterEnd: null
		
    }, o || {});

    return this.each(function() {                           // Returns the element collection. Chainable.

		// vars
        var running = false;
        var div = $(this), ul = $(".album_list", div), v = o.visible;
        var li = $(".album", ul), itemLength = li.size(), curr = o.start, totalSlides = li.length;
		
		// define css for div, ul, etc
        ul.css({ position: "relative"});
		var liMargins = css(li, 'marginLeft') + css(li, 'marginRight') + 2;
		
		var divSize = div[0].offsetWidth-60; 
		
		li.css({ width: ((divSize/v) - (liMargins*v)) });
		var liSize = li[0].offsetWidth + liMargins;

		/*while(liSize-liMargins < 210 && v > 0) {
			v--;
			li.css({ width: ((divSize/v)-(liMargins*v)) });
			liSize = li[0].offsetWidth + liMargins;
			
			console.log(liMargins + " " + divSize + " " + divSize/v + ((divSize/v)-(liMargins*v))*v);
			console.log('---');
		}*/
		
		
		
		// initial hide operation
		hideUnused(li, curr, v, o.speed);
		
		// check to see if any of the controls should be disabled on launch
		$( (curr-o.scroll<0 && o.btnPrev) || (curr+o.scroll+o.visible-1 >= totalSlides && o.btnNext) || [] ).addClass("disabled").attr('disabled', 'disabled');
		
		// controller press
        if(o.btnPrev)
            $(o.btnPrev).click(function() {
                return go(curr-o.scroll);
            });

        if(o.btnNext)
            $(o.btnNext).click(function() {
                return go(curr+o.scroll);
            });

        function go(to) {
			
            if(!running) {
				var bleh = ((curr-to)/o.scroll);
                // If non-circular and to points to first or last, we just return.
                if(to<0 || to>totalSlides) return;
                    else curr = to;

                running = true;
				
				
				$(ul).delay(o.speed).animate({left: bleh*liSize*o.scroll}, o.speed).animate({left: 0}, 0);
				//$(ul).fadeOut(o.speed).animate( {left: -0}, o.speed).fadeIn(o.speed);
				
				// hide divs who are not going to be displayed
				hideUnused(li, curr, v, o.speed);
				
				running = false;
				
                // Disable buttons when the carousel reaches the last/first, and enable when not
				$(o.btnPrev + "," + o.btnNext).removeClass("disabled").removeAttr('disabled');
                $( (curr-o.scroll<0 && o.btnPrev) || (curr+o.scroll+v-1 >= totalSlides && o.btnNext) || [] ).addClass("disabled").attr('disabled', 'disabled');
            }
            return false;
        };
    });
};

function hideUnused(li, curr, v, speed) {
	$.each(li, function(index, value) { 
		var pos = $(this).position().left;
		if((index < curr) ||(index >= (curr+v))) {
			$(this).fadeOut(speed).queue(function(){ 
			  $(this).css({ display: "none" }); 
			  $(this).dequeue(); 
			});
		} else {
			$(this).delay(speed*2).fadeIn(speed).queue(function(){ 
			  $(this).css({ display: "inline-block" }); 
			  $(this).dequeue(); 
			});
		}
	});
};

function css(el, prop) {
    return parseInt($.css(el[0], prop)) || 0;
};


})(jQuery);