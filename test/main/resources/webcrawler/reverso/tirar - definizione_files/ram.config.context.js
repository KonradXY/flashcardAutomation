/*!
 * JavaScript Cookie v2.1.4
 * https://github.com/js-cookie/js-cookie
 *
 * Copyright 2006, 2015 Klaus Hartl & Fagner Brack
 * Released under the MIT license
 */
!function(e){var n=!1;if("function"==typeof define&&define.amd&&(define(e),n=!0),"object"==typeof exports&&(module.exports=e(),n=!0),!n){var o=window.Cookies,t=window.Cookies=e();t.noConflict=function(){return window.Cookies=o,t}}}(function(){function e(){for(var e=0,n={};e<arguments.length;e++){var o=arguments[e];for(var t in o)n[t]=o[t]}return n}function n(o){function t(n,r,i){var c;if("undefined"!=typeof document){if(arguments.length>1){if("number"==typeof(i=e({path:"/"},t.defaults,i)).expires){var a=new Date;a.setMilliseconds(a.getMilliseconds()+864e5*i.expires),i.expires=a}i.expires=i.expires?i.expires.toUTCString():"";try{c=JSON.stringify(r),/^[\{\[]/.test(c)&&(r=c)}catch(e){}r=o.write?o.write(r,n):encodeURIComponent(String(r)).replace(/%(23|24|26|2B|3A|3C|3E|3D|2F|3F|40|5B|5D|5E|60|7B|7D|7C)/g,decodeURIComponent),n=(n=(n=encodeURIComponent(String(n))).replace(/%(23|24|26|2B|5E|60|7C)/g,decodeURIComponent)).replace(/[\(\)]/g,escape);var f="";for(var s in i)i[s]&&(f+="; "+s,!0!==i[s]&&(f+="="+i[s]));return document.cookie=n+"="+r+f}n||(c={});for(var p=document.cookie?document.cookie.split("; "):[],d=/(%[0-9A-Z]{2})+/g,u=0;u<p.length;u++){var l=p[u].split("="),C=l.slice(1).join("=");'"'===C.charAt(0)&&(C=C.slice(1,-1));try{var g=l[0].replace(d,decodeURIComponent);if(C=o.read?o.read(C,g):o(C,g)||C.replace(d,decodeURIComponent),this.json)try{C=JSON.parse(C)}catch(e){}if(n===g){c=C;break}n||(c[g]=C)}catch(e){}}return c}}return t.set=t,t.get=function(e){return t.call(t,e)},t.getJSON=function(){return t.apply({json:!0},[].slice.call(arguments))},t.defaults={},t.remove=function(n,o){t(n,"",e(o,{expires:-1}))},t.withConverter=n,t}return n(function(){})});
Number.isInteger = Number.isInteger || function(value) {return typeof value === "number" && isFinite(value) && Math.floor(value) === value;};

var ramjsCookie = Cookies.noConflict();

var ramjsCDN = "//cdn.reverso.net"+"/ramjs/";
var prebidVersion = "2.23.0", ramjsVersion = "1.0.0.min"; // In case both have the same version. Otherwise versions can be modified independently as required.

[
  ramjsCDN + "ram"+ ramjsVersion + ".js",
  (typeof(abp) !== 'undefined' && !abp) ? ramjsCDN + "predib"+ prebidVersion + ".js" : "",
  (typeof (adblock) != 'undefined') ? ('https:' == document.location.protocol ? 'https:' : 'http:') + '//www.googletagservices.com/tag/js/gpt.js' : ""
].forEach(function(src) {
  if (src !== "") {
    var script = document.createElement('script');
    script.type = "text/javascript";
    script.src = src;
    script.defer = true;
    document.head.appendChild(script);
  }
});

// Init ramjs queue
var ramjs = ramjs || {};
ramjs.que = ramjs.que || [];

ramjs.config = {
    service: 'context',
    experimentID: 'frz5KvKmF',
    nbVariants: 11,
    GA_VARIATION: 0,
    PREBID_TIMEOUT: 1400,
    debug: false,
    optInGracePeriodAfterDeployment: true,
    adContainerPrefix: "div-gpt-ad-", // This will be part of the "code" property passed to prebid. Prebid "code" is "adContainerPrefix + adUnits[i].name"
    siteID: 2629866, // usually same ID for all Reverso sites
    adUnits: [ // array of maps where each map is an ad with its info.
      {
        name: 'Reverso_Ctxt_D_Result_300x250_ATF', // Name of the ad
        slotname: 'Context_300x250_Top',
        mediaTypes: {
            banner: {
                sizes: [[300, 250]]
            }
        },
        bids: [
          {bidder: 'sovrn', params: {tagid: '527038'}},
          {bidder: 'rubicon', params: {accountId: '16826', siteId: '158926', zoneId: '762242', position:'atf'}},
          {bidder: 'ix', params: {siteId: '227191', size: [300, 250]}},
          {bidder: 'appnexus', params: {placementId: '12495040'}}
        ],
        tags: ['desktop', 'initdesktop'], // In order to ease the selection of the ads
      },
      {
        name: 'Reverso_Ctxt_D_Result_300x250_BTF',
        slotname: 'Context_300x250_Bottom',
        mediaTypes: {
            banner: {
                sizes: [[300, 250]]
            }
        },
        bids: [
          {bidder: 'sovrn', params: {tagid: '527039'}},
          {bidder: 'rubicon', params: {accountId: '16826', siteId: '158926', zoneId: '762244', position:'atf'}},
          {bidder: 'ix', params: {siteId: '227192', size: [300, 250]}},
          {bidder: 'appnexus', params: {placementId: '12495041'}}
        ],
        tags: ['desktop', 'initdesktop'], // In order to ease the selection of the ads
      },
      {
        name: 'Reverso_Ctxt_R_Result_InFeed_ATF',
        slotname: 'Context_Native_InResult',
        mediaTypes: {
            banner: {
                sizes: ['fluid', [1,1]]
            }
        },
        bids: [],
        tags: ['native', 'nobid'], // In order to ease the selection of the ads. 'nobid' if prebid shouldn't be applied for this ad.
      },
      {
        name: 'Reverso_Ctxt_D_Result_300x600_BTF',
        slotname: 'Context_300x600',
        mediaTypes: {
            banner: {
                sizes: [[300, 600]]
            }
        },
        bids: [
          {bidder: 'sovrn', params: {tagid: '527040'}},
          {bidder: 'rubicon', params: {accountId: '16826', siteId: '158926', zoneId: '762246'}},
          {bidder: 'ix', params: {siteId: '227193', size: [300, 600]}},
          {bidder: 'appnexus', params: {placementId: '12495042'}}
        ],
        tags: ['desktop', 'large_ad'], // In order to ease the selection of the ads
      },
      {
        name: 'Reverso_Ctxt_D_Result_728x90_BTF',
        slotname: 'Context_MegaBanner_Bottom',
        mediaTypes: {
            banner: {
                sizes: device === "tablet" ? [[728, 90]] : [[970, 90], [728, 90], [994, 250]]
            }
        },
        bids: [
          {bidder: 'sovrn', params: {tagid: '527041'}},
          {bidder: 'rubicon', params: {accountId: '16826', siteId: '158926', zoneId: '762256'}},
          {bidder: 'ix', params: {siteId: '227194', size: [728, 90]}},
          {bidder: 'appnexus', params: {placementId: '12495044'}}
        ],
        tags: ['desktop', 'tablet', 'mega_ad'], // In order to ease the selection of the ads
      },
      {
        name: 'Reverso_Ctxt_M_Result_300x250_BTF',
        slotname: 'Reverso_Ctxt_M_Result_300x250_BTF',
        mediaTypes: {
            banner: {
                sizes: [[300, 250]]
            }
        },
        bids: [
          {bidder: 'sovrn', params: {tagid: '671758'}},
          {bidder: 'rubicon', params: {accountId: '16826', siteId: '158928', zoneId: '1528134'}},
          {bidder: 'ix', params: {siteId: '445100', size: [300, 250]}},
          {bidder: 'appnexus', params: {placementId: '18123864'}}
        ],
        tags: ['mobile', 'mobilebottomexamples'], // In order to ease the selection of the ads
      },
	  {
        name: 'Reverso_Ctxt_M_Result_320x50_ATF',
        slotname: 'Reverso_Ctxt_M_Result_320x50_ATF',
        mediaTypes: {
            banner: {
                sizes: [[320, 50]]
            }
        },
        bids: [
          //{bidder: 'sovrn', params: {tagid: '527042'}},
          {bidder: 'rubicon', params: {accountId: '16826', siteId: '158928', zoneId: '1528132'}},
          {bidder: 'ix', params: {siteId: '445099', size: [320, 50]}},
          {bidder: 'appnexusAst', params: {placementId: '18123865'}}
        ],
        tags: ['mobile', 'mobilefirstexample'], // In order to ease the selection of the ads
      }

    ],
    init: function (adTargeting) {
      ramjs.config.logInfo("ramjs.config.init: start");
      // Disable initial load of ads
      googletag.cmd.push(function() {
        ramjs.config.logInfo("GPT is done loading. Starting to process googletag.cmd queue. Call to pubads().disableInitialLoad().");
        googletag.pubads().disableInitialLoad();
        ramjs.config.setTargeting(adTargeting);
      });

      this.GA_VARIATION = this.getVariation();

      for (var ad in this.adUnits) {
        var aux = this.adUnits[ad];
        if (aux.code === undefined) {
          aux.code = this.adContainerPrefix + aux.name;
        }
      }
      
      ramjs.config.logInfo("ramjs.config.init: end");
    },
    setTargeting: function(adTargeting) {
      adTargeting['abtest'] = ramjs.config.GA_VARIATION || 0;
      
      if(typeof(abp) != 'undefined' && abp) {
        adTargeting['abp'] = '1';
      }
      
      ramjs.config.logInfo("setTargeting array:", adTargeting);
      for (var i in adTargeting) {
        if (typeof(i) !== 'undefined' && typeof(adTargeting[i]) !== 'undefined') { // Be sure that the value is not undefined or null. Otherwise, we display an error message.
          googletag.pubads().clearTargeting(i.toString());
          googletag.pubads().setTargeting(i.toString(), adTargeting[i].toString());
        }
        else {
          ramjs.config.logError("setTargeting: Error while setting GPT targeting. Key or value are undefined or null. Key:", i, "- Value:", adTargeting[i]);
        }
      }
    },
    getVariation: function() {
      var service = this.service,
      experimentID = this.experimentID,
      nbVariants = this.nbVariants;

  		var variant = ramjsCookie.get('experiment_'+service+'_'+experimentID);
  		if(typeof(variant) == 'undefined') {
  			variant = Math.floor(Math.random() * nbVariants);
  			ramjsCookie.set('experiment_'+service+'_'+experimentID, variant, { expires: 180, domain: '.'+location.hostname.split('.').reverse().splice(0,2).reverse().join('.')});
  		}
  		ramjs.config.logInfo('getVariation: experiment_'+service+'_'+experimentID+' = '+variant);
  		return !Number.isInteger(parseInt(variant)) ? 0 : parseInt(variant);
    },
    prebidConfig: function() {
      return { // This map will be passed as parameter to prebid.setConfig
        enableSendAllBids: true,
        bidderSequence: 'random',
        'currency': {'adServerCurrency': 'CAD', 'bidderCurrencyDefault': {'criteo':'CAD', 'ix':'CAD'}},
		userSync: {syncDelay: 5000},
        consentManagement: {
          cmpApi: 'iab',
          timeout: 8000,
          allowAuctionWithoutConsent: true
        },
        debug: ramjs.config.isDebugEnabled()
      }
    },
    _debugProcessed: false,
    isDebugEnabled: function() {
      if (!ramjs.config.debug && !ramjs.config._debugProcessed) {
        cookieDebug = ramjsCookie.get("ramjs_debug");
        cookieDebug = cookieDebug ? cookieDebug : ""
        ramjs.config.debug = "TRUE" === cookieDebug.toUpperCase();
        ramjs.config._debugProcessed = true;
        ramjs.config.logInfo("isDebugEnabled: Debug enabled?", ramjs.config.debug);
      }
      return ramjs.config.debug 
    },
    _setDebugCookie: function(enable) {
      ramjsCookie.set('ramjs_debug', !!enable, { expires: 180, domain: '.'+location.hostname.split('.').reverse().splice(0,2).reverse().join('.')});
      ramjs.config._debugProcessed = false;
      ramjs.config.logInfo("Setting value for ramjs_debug cookie to:", !!enable);
    },
    /**
     * Custom logging
     */
    _firstLoggingMessage: undefined,
    _prepareLogMessage: function (messages, level) {
      if (!ramjs.config._firstLoggingMessage) { ramjs.config._firstLoggingMessage = Date.now(); }
      messageDate = new Date();
      message = [].slice.call(messages);
      level && message.unshift(level);
      message.unshift("display: inline-block; color: #fff; background: #ddd; margin-left: 5px; padding: 1px 4px; border-radius: 3px;");
      message.unshift("display: inline-block; color: #fff; background: #0970ac; padding: 1px 4px; border-radius: 3px;");
      message.unshift("%cRamJS%c" + messageDate.toLocaleString() + " - Elapsed time: " + (messageDate - ramjs.config._firstLoggingMessage) + "ms");
      return message;
    },
    logInfo: function () {
      ramjs.config.isDebugEnabled() && console.info.apply(console, ramjs.config._prepareLogMessage(arguments, "INFO:"));
    },
    logError: function () {
      ramjs.config.isDebugEnabled() && console.error.apply(console, ramjs.config._prepareLogMessage(arguments, "ERROR:"));
    },
    logWarn: function () {
      ramjs.config.isDebugEnabled() && console.warn.apply(console, ramjs.config._prepareLogMessage(arguments, "WARNING:"));
    }
};
