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

        visible: 3,
        start: 0,
        scroll:1,

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
		
		var liMargins = css(li, 'marginLeft') + css(li, 'marginRight') + css(li, 'borderRight') + css(li, 'borderLeft');
		var divSize = ($(div[0]).innerWidth() - 62); 
		
		//li.css({ width: ((divSize/v)-(liMargins*v)) });
		var liSize = li[0].offsetWidth + liMargins;
		div.css({ width: (liSize*o.visible), margin: "0 auto"});
		/*are the lis to small?
		if(css(li, 'min-width')) {
			console.log('LI TO SMALL');
			while(liSize-liMargins<css(li, 'min-width') && v>0) {
				v--;
				console.log(v);
				li.css({ width: ((divSize/v)-(liMargins*v)) });
				console.log((divSize/v)-(liMargins*v));
				liSize = li[0].offsetWidth + liMargins;
			}
		}*/
		
		// initial hide operation
		hideUnused(li, curr, v, 0);
		
		// check to see if any of the controls should be disabled on launch
		$( (curr-o.scroll<0 && o.btnPrev) || (curr+o.scroll+v-1 > totalSlides && o.btnNext) || [] ).addClass("hidden").attr('disabled', 'disabled');
		
		if(totalSlides <= o.visible) {
			$(o.btnPrev).addClass("hidden");
			$(o.btnNext).addClass("hidden");
		}
		
		// controller press
		// todo: add to queue
        if(o.btnPrev)
            $(o.btnPrev).click(function() {
				console.log('prev');
                return go(curr-o.scroll);
            });

        if(o.btnNext)
            $(o.btnNext).click(function() {
				console.log('next');
                return go(curr+o.scroll);
            });

        function go(to) {
			
            if(!running) {
				var bleh = ((curr-to)/o.scroll);
				
                // If non-circular and to points to first or last, we just return.
                if(to<0 || to>totalSlides) return;
                    else curr = to;

                running = true;
				
				// determine which way to animate
				if(o.scroll < o.visible) {
					// hide overflow while changing lis to prevent flickering
					$(div).css({overflow: "hidden"}).delay(o.speed*8).queue(function(){ 
						$(this).css({ overflow: "visible" });
						$(this).dequeue(); 
					});
					
					if(bleh == 1) {  
						$(ul).delay(o.speed).animate({left: liSize*o.scroll}, o.speed).animate({left: 0}, 0);
					} else { 
						$(ul).delay(o.speed).animate({left: liSize*o.scroll}, 0).animate({left: 0}, o.speed);
					}
				}
				
				// hide divs who are not going to be displayed
				hideUnused(li, curr, v, o.speed);

				running = false;
				
                // Disable buttons when the carousel reaches the last/first, and enable when not
				$(o.btnPrev + "," + o.btnNext).removeClass("hidden").removeAttr('disabled');
                $( (curr-o.scroll<0 && o.btnPrev) || (curr+o.scroll+1 > totalSlides && o.btnNext) || [] ).addClass("hidden").attr('disabled', 'disabled');
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
			$(this).delay(speed).fadeIn(speed).queue(function(){ 
			  $(this).css({ display: "block" }); 
			  $(this).dequeue(); 
			});
		}
	});
};

function css(el, prop) {
    return parseInt($.css(el[0], prop)) || 0;
};


})(jQuery);