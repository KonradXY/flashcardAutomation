//If true, the adblocker detector will be activated
var adblockDetectActive = false;

if(adblockDetectActive){
    //First append the css file to the head
    var link = document.createElement('link');    
    link.rel = 'stylesheet';  
    link.type = 'text/css'; 
    link.href = '/2012/style/adBlockerDetector.css';  
    document.getElementsByTagName('HEAD')[0].appendChild(link);

    //Add the needed scripts and html
    document.write(`
    <div id="cXtDeEYgFGTI" class="centerDiv">
        <span id="adbIcon">&nbsp;</span>
        <p>
            Our website is made possible by displaying online advertisements to our visitors.<br>
            Please consider supporting us by disabling your ad blocker.
        </p>
        <div>
            <div id="adbButton">OK</div>
        </div>
    </div>
    <script type="text/javascript">
        var adbdid = Math.random().toString(36).substring(2) + (new Date()).getTime().toString(36);
    </script>
    <script src="/ads.js" type="text/javascript"></script>
    <script type="text/javascript">
        //Check if the fake ads.js script was loaded in the page. and send an event to GA.
        if(!document.getElementById(adbdid)){
            //Ads blocked
            console.log('>>> Adblocker detected.');
            if(typeof ga !=='undefined'){
                ga('send', 'event', 'WR_Adblocker', 'true', {
                    nonInteraction: true,
                    hitCallback: function() {
                        console.log(">>> WR_Adblocker: true -> GA");
                    }
                });
            }

            //document.getElementById('cXtDeEYgFGTI').style.display='block';
        } else {
            //no adblockers
            console.log('>>> No Adblockers detected.');
            if(typeof ga !=='undefined'){
                ga('send', 'event', 'WR_Adblocker', 'false', {
                    nonInteraction: true,
                    hitCallback: function() {
                        console.log(">>> WR_Adblocker: false -> GA");
                    }
                });
            }
        }
    </script>
    `);
}