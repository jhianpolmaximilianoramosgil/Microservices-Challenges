/** 
 * PrimeFaces Pandora Layout
 */
PrimeFaces.widget.Pandora = PrimeFaces.widget.BaseWidget.extend({

    init: function(cfg) {
        this._super(cfg);
        this.wrapper = $(document.body).children('.layout-wrapper');
        var $this = this;

        $(function() {
            $this._init();
        });

        if(!this.wrapper.hasClass('layout-horizontal')) {
            this.restoreMenuState();
        }

        this.expandedMenuitems = this.expandedMenuitems||[];
    },

    _init: function() {
        this.contentWrapper = this.wrapper.children('.layout-main');
        this.topbar = this.wrapper.find('.layout-topbar');
        this.topbarItems = this.topbar.find('.layout-topbar-actions > li.topbar-item');
        this.topbarLinks = this.topbarItems.children('a');
        this.topbarSearchItemMenu = this.topbar.find('.search-item');
        
        this.menuWrapper = this.wrapper.find('.menu-wrapper');
        this.menu = this.menuWrapper.find('.layout-menu');
        this.menuButton = this.topbar.find('.menu-button');
        this.menulinks = this.menu.find('a');

        this.profileContainer = $('.layout-profile');
        this.profileButton = this.profileContainer.children('.layout-profile-button');
        this.profileMenu = this.profileContainer.children('.layout-profile-menu');
        this.profileImage = this.profileButton.children('.profile-image-icons');

        this.rightpanel = this.wrapper.find('.layout-rightpanel');
        this.rightpanelButton = this.topbar.find('.layout-rightpanel-button');
        this.rightpanelExitButton = this.rightpanel.find('.rightpanel-exit-button');

        this.configButton = $('.layout-config-button');
        this.configMenu = $('.layout-config');
        this.configMenuClose = this.configMenu.find('> .layout-config-content > .layout-config-close');

        this.bindEvents();
    },

    toggleClass: function(el, className) {
        if (el.hasClass(className)) {
            el.removeClass(className);
        }
        else {
            el.addClass(className);
        }
    },
    
    bindEvents: function() {
        var $this = this;

        this.bindTopbarEvents();
        this.bindMenuEvents();
        this.bindRightPanelEvents();
        this.bindConfigEvents();

        $(document.body).off('click.layoutBody').on('click.layoutBody', function() {
            if (!$this.menuClick) {
                $this.wrapper.removeClass('layout-overlay-active layout-mobile-active');
                $(document.body).removeClass('blocked-scroll');

                if ($this.isHorizontal() || $this.isSlim() || $this.isSlimPlus() ) {
                    $this.menu.find('.active-menuitem').removeClass('active-menuitem');
                    $this.menuActive = false;
                } 
            }

            if (!$this.profileMenuClick && ($this.isHorizontal() || $this.isSlim() || $this.isSlimPlus())) {
                $this.profileContainer.removeClass('layout-profile-active');
            }

            if (!$this.topbarItemClicked) {
                $this.removeTopbarClassFromAllItems(null, 'active-topmenuitem', $this.topbarItems.filter('.active-topmenuitem'));
            }

            if (!$this.rightpanelClicked) {
                $this.wrapper.removeClass('layout-rightpanel-active');
            }

            if (!$this.configMenuClicked && $this.configMenu.hasClass('layout-config-active')) {
                if (!$this.wrapper.hasClass('layout-mobile-active') && !$this.wrapper.hasClass('layout-overlay-active')) {
                    $(document.body).removeClass('blocked-scroll');
                }
                $this.configMenu.removeClass('layout-config-active');
            }

            $this.topbarItemClicked = false;
            $this.rightpanelClicked = false;
            $this.menuClick = false;
            $this.profileMenuClick = false;
            $this.configMenuClicked = false;
        });
    },

    bindConfigEvents: function() {
        var $this = this;
        
        this.configButton.off('click.config').on('click.config', function(e) {
            $this.configMenuClicked = true;

            if ($this.configMenu.hasClass('layout-config-active')) {
                $this.configMenu.removeClass('layout-config-active');
                $(document.body).removeClass('blocked-scroll');
            }
            else {
                $this.configMenu.addClass('layout-config-active');
                $(document.body).addClass('blocked-scroll');
            }
            
            e.preventDefault();
        });

        this.configMenuClose.off('click.config').on('click.config', function(e) {
            $this.configMenu.removeClass('layout-config-active');
            $this.configMenuClicked = true;
            $(document.body).removeClass('blocked-scroll');
            e.preventDefault();
        });
        
        this.configMenu.off('click.configMenu').on('click.configMenu', function() {
            $this.configMenuClicked = true;
        });
    },

    bindMenuEvents: function() {
        var $this = this;

        this.menuWrapper.off('click.menu').on('click.menu', function() {
            if (!$this.profileMenuClick) {
                $this.menuClick = true;
            } 
        });

        this.menuButton.off('click.menu').on('click.menu', function(e) {
            $this.menuClick = true;

            if ($this.isMobile()) {
                if ($this.wrapper.hasClass('layout-mobile-active')) {
                    $this.wrapper.removeClass('layout-mobile-active');
                    $(document.body).removeClass('blocked-scroll');
                }
                else {
                    $this.wrapper.addClass('layout-mobile-active');
                    $(document.body).addClass('blocked-scroll');
                }
            }
            else {
                if ($this.isStatic()) {
                    $this.wrapper.toggleClass('layout-static-active');
                }
                else {
                    $this.wrapper.toggleClass('layout-overlay-active');
                }
            }

            e.preventDefault();
        });
        
        this.menulinks.off('click.menu').on('click.menu', function(e) {
            var link = $(this),
            item = link.parent(),
            submenu = item.children('ul');
            horizontal = $this.isHorizontal();
            $this.menuClick = true;

            if ($this.isHorizontal() || $this.isSlim() || $this.isSlimPlus()) {
                submenu.css('display','');
                
                if($this.profileMenu){
                    $this.profileMenu.css('display', '');
                }

                if (item.hasClass('active-menuitem')) {
                    if (submenu.length) {
                        item.removeClass('active-menuitem');
                        e.preventDefault();
                    }
    
                    if (item.parent().is($this.jq)) {
                        $this.menuActive = false;
                    }
                }
                else {
                    if (submenu.length > 0) {
                        e.preventDefault();
                    }
                    
                    item.siblings('.active-menuitem').removeClass('active-menuitem');
                    item.addClass('active-menuitem');
                    
                    if (item.parent().is($this.jq)) {
                        $this.menuActive = true;
                    }
                }
            }
            else {
                if(item.hasClass('active-menuitem')) {
                    if(submenu.length) {
                        $this.removeMenuitem(item.attr('id'));
                        item.removeClass('active-menuitem');
                        
                        if(horizontal) {
                            if(item.parent().is($this.jq)) {
                                $this.menuActive = false;
                            }
                            $this.removeMenuitem(item.attr('id'));
                            item.removeClass('active-menuitem');
                        }
                        else {
                            submenu.slideUp(function() {
                                $this.removeMenuitem(item.attr('id'));
                                item.removeClass('active-menuitem');
                            });
                        }
                    }
                }
                else {
                    $this.addMenuitem(item.attr('id'));
    
                    if(horizontal) {
                        $this.deactivateItems(item.siblings());
                        item.addClass('active-menuitem');
                        $this.menuActive = true;
                    }
                    else {
                        $this.deactivateItems(item.siblings(), true);
                        var groupedItems = item.parent().closest('li');
                        if (groupedItems && groupedItems.length > 0) {
                            $this.deactivateItems(groupedItems.siblings(), true);
                        }
                        $this.activate(item);
                    }
                }
            }
                                                
            if(submenu.length) {
                e.preventDefault();
            }
        });

        this.menu.find('ul').off('click.menu').on('click.menu', function() {
            if ($this.isHorizontal()) {
                $this.horizontalMenuClick = true;
            }
        });

        this.profileImage.off('click.profile').on('click.profile', function (e) {
            if (!$this.isHorizontal() && !$this.isSlim() && !$this.isSlimPlus()) {
                e.stopPropagation();
            }
        });

        this.profileButton.off('click.profile').on('click.profile', function (e) {
            if (!$this.isHorizontal() && !$this.isSlim() && !$this.isSlimPlus()) {
                $this.profileContainer.toggleClass('layout-profile-active');
                $this.profileMenu.slideToggle();
            }
            else {
                $this.profileMenu.css('display', '');
                $this.profileMenuClick = true;

                if ($this.profileContainer.hasClass('layout-profile-active')) {
                    $this.profileMenu.addClass('fadeOutUp');

                    setTimeout(function () {
                        $this.profileContainer.removeClass('layout-profile-active');
                        $this.profileMenu.removeClass('fadeOutUp');
                    }, 150);
                }
                else {
                    $this.profileContainer.addClass('layout-profile-active');
                    $this.profileMenu.addClass('fadeInDown');
                }
            }

            e.preventDefault();
        });
        
        this.menu.find('> li').off('mouseenter.menu').on('mouseenter.menu', function(e) {    
            if(($this.isHorizontal()|| $this.isSlim() || $this.isSlimPlus()) && $this.menuActive) {
                var item = $(this);
                
                if(!item.hasClass('active-menuitem')) {
                    $this.menu.find('.active-menuitem').removeClass('active-menuitem');
                    
                    if($this.menuActive) {
                        item.addClass('active-menuitem');
                    }
                }
            }
        });
    },
    
    bindTopbarEvents: function() {
        var $this = this;

        this.topbarLinks.off('click.topbar').on('click.topbar', function(e) {
            var link = $(this),
            item = link.parent(),
            submenu = item.children('ul');

            if ($this.isMobile()) {
                $this.removeTopbarClassFromAllItems(null, 'active-topmenuitem', $this.topbarItems.filter('.active-topmenuitem').not(item));
            }
            else {
                $this.removeTopbarClassFromAllItems(item, 'active-topmenuitem');
            }
            $this.addTopbarClass(item, 'active-topmenuitem');

            $this.topbarItemClicked = true;
            
            if (submenu.length) {
                e.preventDefault();
            }
        });

        this.topbarSearchItemMenu.off('click.topbar').on('click.topbar', function(e) {
            $this.topbarItemClicked = true;
        });
    },

    bindRightPanelEvents: function() {
        var $this = this;
        var changeRightpanelState = function(e) {
            this.toggleClass(this.wrapper, 'layout-rightpanel-active');
            
            this.rightpanelClicked = true;
            e.preventDefault();
        };
        
        this.rightpanelButton.off('click.rightpanel').on('click.rightpanel', changeRightpanelState.bind(this));
        this.rightpanelExitButton.off('click.rightpanel').on('click.rightpanel', changeRightpanelState.bind(this));
        
        this.rightpanel.off('click.rightpanel').on('click.rightpanel', function() {
            $this.rightpanelClicked = true;
        });
    },

    activate: function(item) {
        var submenu = item.children('ul');
        item.addClass('active-menuitem');

        if(submenu.length) {
            submenu.slideDown();
        }
    },

    deactivate: function(item) {
        var submenu = item.children('ul');
        item.removeClass('active-menuitem');
        
        if(submenu.length) {
            submenu.hide();
        }
    },
            
    deactivateItems: function(items, animate) {
        var $this = this;
        
        for(var i = 0; i < items.length; i++) {
            var item = items.eq(i),
            submenu = item.children('ul');
            
            if(submenu.length) {
                if(item.hasClass('active-menuitem')) {
                    var activeSubItems = item.find('.active-menuitem');
                    item.removeClass('active-menuitem');
                    
                    if(animate) {
                        submenu.slideUp('normal', function() {
                            $(this).parent().find('.active-menuitem').each(function() {
                                $this.deactivate($(this));
                            });
                        });
                    }
                    else {
                        item.find('.active-menuitem').each(function() {
                            $this.deactivate($(this));
                        });
                    }
                    
                    $this.removeMenuitem(item.attr('id'));
                    activeSubItems.each(function() {
                        $this.removeMenuitem($(this).attr('id'));
                    });
                }
                else {
                    item.find('.active-menuitem').each(function() {
                        var subItem = $(this);
                        $this.deactivate(subItem);
                        $this.removeMenuitem(subItem.attr('id'));
                    });
                }
            }
            else if(item.hasClass('active-menuitem')) {
                $this.deactivate(item);
                $this.removeMenuitem(item.attr('id'));
            }
        }
    },
    
    removeMenuitem: function (id) {
        this.expandedMenuitems = $.grep(this.expandedMenuitems, function (value) {
            return value !== id;
        });
        this.saveMenuState();
    },
    
    addMenuitem: function (id) {
        if ($.inArray(id, this.expandedMenuitems) === -1) {
            this.expandedMenuitems.push(id);
        }
        this.saveMenuState();
    },
    
    saveMenuState: function() {
        if(this.isHorizontal()) {
            return;
        }
        
        if(this.wrapper.hasClass('layout-wrapper-static'))
            $.cookie('pandora_menu_static', 'pandora_menu_static', {path: '/'});
        else
            $.removeCookie('pandora_menu_static', {path: '/'});
        
        $.cookie('pandora_expandeditems', this.expandedMenuitems.join(','), {path: '/'});
    },
    
    clearMenuState: function() {
        this.expandedMenuitems = [];
        $.removeCookie('pandora_expandeditems', {path: '/'});
        $.removeCookie('pandora_menu_static', {path: '/'});
    },

    clearActiveItems: function() {
        var activeItems = this.jq.find('li.active-menuitem'),
        subContainers = activeItems.children('ul');

        activeItems.removeClass('active-menuitem');
        if(subContainers && subContainers.length) {
            subContainers.hide();
        }
    },

    clearLayoutState: function() {
        this.clearMenuState();
        this.clearActiveItems();
    },
    
    restoreMenuState: function() {
        var menuCookie = $.cookie('pandora_expandeditems');
        if (menuCookie) {
            this.expandedMenuitems = menuCookie.split(',');
            for (var i = 0; i < this.expandedMenuitems.length; i++) {
                var id = this.expandedMenuitems[i];
                if (id) {
                    var menuitem = $("#" + this.expandedMenuitems[i].replace(/:/g, "\\:"));
                    menuitem.addClass('active-menuitem');
                    
                    var submenu = menuitem.children('ul');
                    if(submenu.length) {
                        submenu.show();
                    }
                }
            }
        }
        
        var sidebarCookie = $.cookie('pandora_menu_static');
        if(sidebarCookie) {
            this.wrapper.addClass('layout-wrapper-static layout-wrapper-static-restore');
        }
    },
    
    removeTopbarClassFromAllItems: function(item, className, items) {
        var activeItems = item != null ? item.siblings('.' + className) : items;

        activeItems.removeClass(className);
        activeItems.children('ul').removeClass('fadeInDown');
    },
    
    addTopbarClass: function(item, className) {
        var submenu = item.children('ul');
        
        if (submenu.length) {
            if (item.hasClass(className)) {
                submenu.removeClass('fadeInDown').addClass('fadeOutUp');

                setTimeout(function() {
                    item.removeClass(className);
                    submenu.removeClass('fadeOutUp');
                }, 100);
            }
            else {
                item.addClass(className);
                submenu.addClass('fadeInDown');
            }
        }
    },

    hideTopBar: function() {
        var $this = this;
        this.topbarMenu.addClass('fadeOutUp');
        
        setTimeout(function() {
            $this.topbarMenu.removeClass('fadeOutUp topbar-menu-visible');
        },500);
    },
    
    isMobile: function() {
        return window.innerWidth <= 992;
    },

    isHorizontal: function() {
        return this.wrapper.hasClass('layout-horizontal') && !this.isMobile();
    },
    isSlim: function() {
        return this.wrapper.hasClass('layout-slim') && !this.isMobile();
    },
    isSlimPlus: function() {
        return this.wrapper.hasClass('layout-slim-plus') && !this.isMobile();
    },
    isStatic: function() {
        return this.wrapper.hasClass('layout-static') && !this.isMobile();
    }
});

PrimeFaces.PandoraConfigurator = {
    
    configOptions: $('.layout-config .layout-config-option'),
    
    disableThemeSelection: function() {
        this.configOptions.css('pointer-events', 'none');
    },
    
    enableThemeSelection: function() {
        this.configOptions.css('pointer-events', 'auto');
    },

    changePalette: function(name, menuTheme, topbarTheme, componentTheme) {
        var $this = this;
        var types = ['menu', 'component'];
        var callback = function(loadedType) {
            types = types.filter(function(type) { return loadedType !== type; });
            if (types.length === 0) {
                $this.enableThemeSelection();
            }
        };
        
        this.changeMenuTheme(name, menuTheme, {type: 'menu', callback: callback});
        this.changeComponentsTheme(componentTheme, {type: 'component', callback: callback});
        this.changeSectionTheme(topbarTheme, 'layout-topbar');
    },

    changeComponentsTheme: function(theme, themeParam) {
        var library = 'primefaces-pandora';
        var linkElement = $('link[href*="theme.css"]');
        var href = linkElement.attr('href');
        var index = href.indexOf(library) + 1;
        var currentTheme = href.substring(index + library.length);

        if (currentTheme !== theme) {
            this.replaceLink(linkElement, href.replace(currentTheme, theme), themeParam);
        }
    },

    changePrimaryColor: function(newColor, themeParam) {
        var linkElement = $('link[href*="layout-"]');
        var href = linkElement.attr('href');
        var startIndexOf = href.indexOf('layout-') + 7;
        var endIndexOf = href.indexOf('.css');
        var currentColor = href.substring(startIndexOf, endIndexOf);

        if (currentColor !== newColor) {
            this.replaceLink(linkElement, href.replace(currentColor, newColor), themeParam);
        }
    },
    
    changeMenuTheme: function(name, theme, themeParam) {
        if (name === 'colored') {
            this.changeSectionTheme(theme, 'layout-menu');
        }
        else {
            this.changeSectionTheme(name, 'layout-menu');
            this.changePrimaryColor(theme, themeParam);
        }
    },

    changeSectionTheme: function(theme, section) {
        var wrapperElement = $('.layout-wrapper');
        var styleClass = wrapperElement.attr('class');
        var tokens = styleClass.split(' ');
        var sectionClass;
        for (var i = 0; i < tokens.length; i++) {
            if (tokens[i].indexOf(section + '-') > -1) {
                sectionClass = tokens[i];
                break;
            }
        }
        
        wrapperElement.attr('class', styleClass.replace(sectionClass, section + '-' + theme));   
    },

    beforeResourceChange: function() {
        PrimeFaces.ajax.RESOURCE = null;    //prevent resource append
    },

    replaceLink: function(linkElement, href, themeParam) {
        PrimeFaces.ajax.RESOURCE = 'javax.faces.Resource';
        
        var isIE = this.isIE();

        if (isIE) {
            linkElement.attr('href', href);
        }
        else {
            this.disableThemeSelection();

            var $this = this,
            cloneLinkElement = linkElement.clone(false);

            cloneLinkElement.attr('href', href);
            linkElement.after(cloneLinkElement);
            
            cloneLinkElement.off('load').on('load', function() {
                linkElement.remove();
                
                if (themeParam) {
                    themeParam.callback(themeParam.type);
                }
                else {
                    $this.enableThemeSelection();
                }
            });
        }
    },
    
    changeMenuToStatic: function() {
        $('.layout-wrapper').removeClass('layout-overlay layout-horizontal layout-slim layout-slim-plus').addClass('layout-static layout-static-active');
        this.clearLayoutState();
    },

    changeMenuToOverlay: function() {
        $('.layout-wrapper').removeClass('layout-horizontal layout-static layout-slim layout-static-active layout-slim-plus').addClass('layout-overlay');
        this.clearLayoutState();
    },

    changeMenuToHorizontal: function() {
        $('.layout-wrapper').removeClass('layout-overlay layout-slim layout-static layout-static-active layout-slim-plus').addClass('layout-horizontal');
        this.clearLayoutState();
    },

    changeMenuToSlim: function() {
        $('.layout-wrapper').removeClass('layout-overlay layout-horizontal layout-static layout-static-active layout-slim-plus').addClass('layout-slim');
        this.clearLayoutState();
    },

    changeMenuToSlimPlus: function() {
        $('.layout-wrapper').removeClass('layout-overlay layout-horizontal layout-static layout-static-active layout-slim').addClass('layout-slim-plus');
        this.clearLayoutState();
    },

    clearLayoutState: function() {
        var menu = PF('PandoraMenuWidget');

        if (menu) {
            menu.clearLayoutState();
        }
    },
    
    isIE: function() {
        return /(MSIE|Trident\/|Edge\/)/i.test(navigator.userAgent);
    }
};

/*!
 * jQuery Cookie Plugin v1.4.1
 * https://github.com/carhartl/jquery-cookie
 *
 * Copyright 2006, 2014 Klaus Hartl
 * Released under the MIT license
 */
(function (factory) {
	if (typeof define === 'function' && define.amd) {
		// AMD (Register as an anonymous module)
		define(['jquery'], factory);
	} else if (typeof exports === 'object') {
		// Node/CommonJS
		module.exports = factory(require('jquery'));
	} else {
		// Browser globals
		factory(jQuery);
	}
}(function ($) {

	var pluses = /\+/g;

	function encode(s) {
		return config.raw ? s : encodeURIComponent(s);
	}

	function decode(s) {
		return config.raw ? s : decodeURIComponent(s);
	}

	function stringifyCookieValue(value) {
		return encode(config.json ? JSON.stringify(value) : String(value));
	}

	function parseCookieValue(s) {
		if (s.indexOf('"') === 0) {
			// This is a quoted cookie as according to RFC2068, unescape...
			s = s.slice(1, -1).replace(/\\"/g, '"').replace(/\\\\/g, '\\');
		}

		try {
			// Replace server-side written pluses with spaces.
			// If we can't decode the cookie, ignore it, it's unusable.
			// If we can't parse the cookie, ignore it, it's unusable.
			s = decodeURIComponent(s.replace(pluses, ' '));
			return config.json ? JSON.parse(s) : s;
		} catch(e) {}
	}

	function read(s, converter) {
		var value = config.raw ? s : parseCookieValue(s);
		return $.isFunction(converter) ? converter(value) : value;
	}

	var config = $.cookie = function (key, value, options) {

		// Write

		if (arguments.length > 1 && !$.isFunction(value)) {
			options = $.extend({}, config.defaults, options);

			if (typeof options.expires === 'number') {
				var days = options.expires, t = options.expires = new Date();
				t.setMilliseconds(t.getMilliseconds() + days * 864e+5);
			}

			return (document.cookie = [
				encode(key), '=', stringifyCookieValue(value),
				options.expires ? '; expires=' + options.expires.toUTCString() : '', // use expires attribute, max-age is not supported by IE
				options.path    ? '; path=' + options.path : '',
				options.domain  ? '; domain=' + options.domain : '',
				options.secure  ? '; secure' : ''
			].join(''));
		}

		// Read

		var result = key ? undefined : {},
			// To prevent the for loop in the first place assign an empty array
			// in case there are no cookies at all. Also prevents odd result when
			// calling $.cookie().
			cookies = document.cookie ? document.cookie.split('; ') : [],
			i = 0,
			l = cookies.length;

		for (; i < l; i++) {
			var parts = cookies[i].split('='),
				name = decode(parts.shift()),
				cookie = parts.join('=');

			if (key === name) {
				// If second argument (value) is a function it's a converter...
				result = read(cookie, value);
				break;
			}

			// Prevent storing a cookie that we couldn't decode.
			if (!key && (cookie = read(cookie)) !== undefined) {
				result[name] = cookie;
			}
		}

		return result;
	};

	config.defaults = {};

	$.removeCookie = function (key, options) {
		// Must not alter options, thus extending a fresh object...
		$.cookie(key, '', $.extend({}, options, { expires: -1 }));
		return !$.cookie(key);
	};

}));

/* JS extensions to support material animations */
if(PrimeFaces.widget.InputSwitch) {
    PrimeFaces.widget.InputSwitch = PrimeFaces.widget.InputSwitch.extend({
         
         init: function(cfg) {
             this._super(cfg);
             
             if(this.input.prop('checked')) {
                 this.jq.addClass('ui-inputswitch-checked');
             }
         },
         
         toggle: function() {
             var $this = this;

             if(this.input.prop('checked')) {
                 this.uncheck(); 
                 setTimeout(function() {
                    $this.jq.removeClass('ui-inputswitch-checked');
                 }, 100);
             }
             else {
                 this.check();
                 setTimeout(function() {
                    $this.jq.addClass('ui-inputswitch-checked');
                 }, 100);
             }
         }
    });
}

if(PrimeFaces.widget.SelectBooleanButton) {
    PrimeFaces.widget.SelectBooleanButton.prototype.check = function() {
        if(!this.disabled) {
            this.input.prop('checked', true);
            this.jq.addClass('ui-state-active').children('.ui-button-text').contents()[0].textContent = this.cfg.onLabel;

            if(this.icon.length > 0) {
                this.icon.removeClass(this.cfg.offIcon).addClass(this.cfg.onIcon);
            }

            this.input.trigger('change');
        }
    }
    
    PrimeFaces.widget.SelectBooleanButton.prototype.uncheck = function() {
        if(!this.disabled) {
            this.input.prop('checked', false);
            this.jq.removeClass('ui-state-active').children('.ui-button-text').contents()[0].textContent = this.cfg.offLabel;

            if(this.icon.length > 0) {
                this.icon.removeClass(this.cfg.onIcon).addClass(this.cfg.offIcon);
            }

            this.input.trigger('change');
        }
    }
}

PrimeFaces.skinInput = function(input) {
    setTimeout(function() {
        if(input.val() != '') {
            var parent = input.parent();
            input.addClass('ui-state-filled');
            
            if(parent.is("span:not('.md-inputfield')")) {
                parent.addClass('md-inputwrapper-filled');
            }
        }
    }, 1);
    
    input.on('mouseenter', function() {
        $(this).addClass('ui-state-hover');
    })
    .on('mouseleave', function() {
        $(this).removeClass('ui-state-hover');
    })
    .on('focus', function() {
        var parent = input.parent();
        $(this).addClass('ui-state-focus');
        
        if(parent.is("span:not('.md-inputfield')")) {
            parent.addClass('md-inputwrapper-focus');
        }
    })
    .on('blur', function() {
        $(this).removeClass('ui-state-focus');

        if(input.hasClass('hasDatepicker')) {
            setTimeout(function() {
                PrimeFaces.onInputBlur(input);
            },150);
        }
        else {
            PrimeFaces.onInputBlur(input);
        }
    });

    //aria
    input.attr('role', 'textbox')
            .attr('aria-disabled', input.is(':disabled'))
            .attr('aria-readonly', input.prop('readonly'));

    if(input.is('textarea')) {
        input.attr('aria-multiline', true);
    }

    return this;
};

PrimeFaces.onInputBlur = function(input) {
    var parent = input.parent(),
    hasInputFieldClass = parent.is("span:not('.md-inputfield')");
    
    if(parent.hasClass('md-inputwrapper-focus')) {
        parent.removeClass('md-inputwrapper-focus');
    }
    
    if(input.val() != '') {
        input.addClass('ui-state-filled');
        if(hasInputFieldClass) {
            parent.addClass('md-inputwrapper-filled');
        }
    }
    else {
        input.removeClass('ui-state-filled');
        parent.removeClass('md-inputwrapper-filled');
    }    
};

if(PrimeFaces.widget.AutoComplete) {
    PrimeFaces.widget.AutoComplete.prototype.setupMultipleMode = function() {
        var $this = this;
        this.multiItemContainer = this.jq.children('ul');
        this.inputContainer = this.multiItemContainer.children('.ui-autocomplete-input-token');

        this.multiItemContainer.hover(function() {
                $(this).addClass('ui-state-hover');
            },
            function() {
                $(this).removeClass('ui-state-hover');
            }
        ).click(function() {
            $this.input.focus();
        });

        //delegate events to container
        this.input.focus(function() {
            $this.multiItemContainer.addClass('ui-state-focus');
            $this.jq.addClass('md-inputwrapper-focus');
        }).blur(function(e) {
            $this.multiItemContainer.removeClass('ui-state-focus');
            $this.jq.removeClass('md-inputwrapper-focus').addClass('md-inputwrapper-filled');
            
            setTimeout(function() {
                if($this.hinput.children().length == 0 && !$this.multiItemContainer.hasClass('ui-state-focus')) {
                    $this.jq.removeClass('md-inputwrapper-filled');
                }
            }, 150); 
        });

        var closeSelector = '> li.ui-autocomplete-token > .ui-autocomplete-token-icon';
        this.multiItemContainer.off('click', closeSelector).on('click', closeSelector, null, function(event) {
            if($this.multiItemContainer.children('li.ui-autocomplete-token').length === $this.cfg.selectLimit) {
                if(PrimeFaces.isIE(8)) {
                    $this.input.val('');
                }
                $this.input.css('display', 'inline');
                $this.enableDropdown();
            }
            $this.removeItem(event, $(this).parent());
        });
    };
};

if(PrimeFaces.widget.Calendar) {
    PrimeFaces.widget.Calendar.prototype.bindDateSelectListener = function() {
        var _self = this;

        this.cfg.onSelect = function() {
            if(_self.cfg.popup) {
                _self.fireDateSelectEvent();
            }
            else {
                var newDate = $.datepicker.formatDate(_self.cfg.dateFormat, _self.getDate());

                _self.input.val(newDate);
                _self.fireDateSelectEvent();
            }
            
            if(_self.input.val() != '') {
               var parent = _self.input.parent();
               parent.addClass('md-inputwrapper-filled');
               _self.input.addClass('ui-state-filled');
           }
        };
    };
}

/* Issue #924 is fixed for 5.3+ and 6.0. (compatibility with 5.3) */
if(window['PrimeFaces'] && window['PrimeFaces'].widget.Dialog) {
    PrimeFaces.widget.Dialog = PrimeFaces.widget.Dialog.extend({
        
        enableModality: function() {
            this._super();
            $(document.body).children(this.jqId + '_modal').addClass('ui-dialog-mask');
        },
        
        syncWindowResize: function() {}
    });
}

/* Issue #2131 */
if(window['PrimeFaces'] && window['PrimeFaces'].widget.Schedule && isLtPF8Version()) {
    PrimeFaces.widget.Schedule = PrimeFaces.widget.Schedule.extend({
        
        setupEventSource: function() {
            var $this = this,
            offset = moment().utcOffset()*60000;

            this.cfg.events = function(start, end, timezone, callback) {
                var options = {
                    source: $this.id,
                    process: $this.id,
                    update: $this.id,
                    formId: $this.cfg.formId,
                    params: [
                        {name: $this.id + '_start', value: start.valueOf() + offset},
                        {name: $this.id + '_end', value: end.valueOf() + offset}
                    ],
                    onsuccess: function(responseXML, status, xhr) {
                        PrimeFaces.ajax.Response.handle(responseXML, status, xhr, {
                                widget: $this,
                                handle: function(content) {
                                    callback($.parseJSON(content).events);
                                }
                            });

                        return true;
                    }
                };

                PrimeFaces.ajax.Request.handle(options);
            }
        }
    });
}

if(PrimeFaces.widget.SelectOneMenu) {
    PrimeFaces.widget.SelectOneMenu = PrimeFaces.widget.SelectOneMenu.extend({
        init: function(cfg) {
            this._super(cfg);

            var $this = this;
            if(!this.disabled && this.jq.parent().hasClass('md-inputfield')) {
                this.itemsContainer.children('.ui-selectonemenu-item:first').css({'display': 'none'});
                if (this.input.val() != "") {
                    this.jq.addClass("ui-state-filled");
                }

                this.input.off('change').on('change', function() {
                    $this.inputValueControl($(this));
                });
                
                if(this.cfg.editable) {
                    this.label.on('input', function(e) {
                        $this.inputValueControl($(this));
                    }).on('focus', function() {
                        $this.jq.addClass('ui-state-focus');
                    }).on('blur', function() {
                        $this.jq.removeClass('ui-state-focus');
                        $this.inputValueControl($(this));
                    });
                }
            }
        },
        
        inputValueControl: function(input) {
            if (input.val() != "")
                this.jq.addClass("ui-state-filled"); 
            else
                this.jq.removeClass("ui-state-filled");
        }
    });
}

if(PrimeFaces.widget.Calendar) {
    (function () {
        /* Find an object's position on the screen. */
        if ($.datepicker) {
            $.datepicker._findPos = function( obj ) {
                var position,
                    inst = this._getInst( obj ),
                    isRTL = this._get( inst, "isRTL" );

                while ( obj && ( obj.type === "hidden" || obj.nodeType !== 1 || $.expr.filters.hidden( obj ) ) ) {
                    var temp = obj[ isRTL ? "previousSibling" : "nextSibling" ];
                    obj = temp || obj.parentElement;
                }
                if (obj) {
                    position = $( obj ).offset();
                    return [ position.left, position.top ];
                }
                return [0, 0];
            };
        }
    })();
}

function isLtPF8Version() {
    var version = window['PrimeFaces'].VERSION;
    if (!version) {
        return true;
    }

    return parseInt(version.split('.')[0], 10) < 8;
}