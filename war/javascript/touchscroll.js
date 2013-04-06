TouchScroll.config={threshold:5,scrollHandleMinSize:25,flicking:{triggerThreshold:250,friction:0.998,minSpeed:0.15,timingFunc:[0,0.3,0.6,1]},elasticity:{factorDrag:0.5,factorFlick:0.2,max:100},snapBack:{timingFunc:[0,0.25,0,1],defaultTime:750,alwaysDefaultTime:true}};TouchScroll._hasTouchSupport=(function(){if("createTouch" in document){return true}try{var b=document.createEvent("TouchEvent");return !!b.initTouchEvent}catch(a){return false}}());TouchScroll._parsesMatrixCorrectly=(function(){var a=new WebKitCSSMatrix("matrix(1, 0, 0, 1, -20, -30)");return a.e===-20&&a.f===-30}());TouchScroll._android=(function(){var a=navigator.userAgent.match(/Android\s+(\d+(?:\.\d+)?)/);return a&&parseFloat(a[1])}());TouchScroll._eventNames={start:"touchstart",move:"touchmove",end:"touchend",cancel:"touchcancel"};if(!TouchScroll._hasTouchSupport){TouchScroll._eventNames={start:"mousedown",move:"mousemove",end:"mouseup",cancel:"touchcancel"}}TouchScroll._styleSheet=(function(){var e=document;var c=e.querySelector("head")||e.documentElement;var d=document.createElement("style");c.insertBefore(d,c.firstChild);for(var b=0,a;(a=e.styleSheets[b]);b++){if(d===a.ownerNode){return a}}return e.styleSheets[0]}());[".TouchScroll{position:relative;display:-webkit-box;}",".-ts-layer{-webkit-transition-property:-webkit-transform;-webkit-transform:translate3d(0,0,0);position:absolute;height:100%;top:0;right:0;left:0;}",".-ts-outer{-webkit-box-flex:1;position:relative;height:auto;}",".-ts-inner {position:relative;-webkit-transform-style:flat;}",".scrolling>.-ts-inner{-webkit-user-select:none;pointer-events:none;}",".-ts-bars{bottom:0;left:0;overflow:hidden;pointer-events:none;position:absolute;opacity:0;right:0;top:0;z-index:2147483647;-webkit-transition:opacity 250ms;}",".-ts-bars-active{opacity:1;-webkit-transition:none;}",".-ts-bar{display:none;position:absolute;right:3px;bottom:3px;}",".-ts-bar.active{display:block;}",".-ts-bar-e{height:7px;left:3px;-webkit-transform:rotate(-90deg) translateX(-7px);-webkit-transform-origin:0 0;}",".-ts-bar-f{width:7px;top:3px;}",".-ts-bars-both .-ts-bar-e{right:9px;}",".-ts-bars-both .-ts-bar-f{bottom:9px;}",".-ts-indicator-e,.-ts-indicator-f,.-ts-bar-part{position:absolute;}",".-ts-bar-part{width: 7px;-webkit-border-image:url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAA4AAAAOBAMAAADtZjDiAAAALVBMVEUAAAD///8AAAD///+kpKT///////////////////8nJycBAQEJCQn///9YWFgbIh+zAAAAD3RSTlOATQASUTVFQwMkaIB3TFtjuC6yAAAATElEQVQI12NQ0piaFtmkxKBkLigoWKzEoHzR68wSWSOGdrkNDNwPKxgmejMwMGyRZAhcAKS5RBkSDwBpHjE4DROHqYPpg5kDMxdqDwDB4xorHHHNdAAAAABJRU5ErkJggg==) 6 stretch;-webkit-box-sizing:border-box;-webkit-transform-origin:0 0;}",".-ts-bar-1,.-ts-bar-3{border-width:3px 3px 0;height:0;}",".-ts-bar-3{border-width:0 3px 3px;}",".-ts-bar-2{height:1px;border-width:0 3px;}",".-ts-bar-2{height:1px;}"].forEach(function(b,a){this.insertRule(b,a)},TouchScroll._styleSheet);function TouchScroll(d,b){b=b||{};this.elastic=!!b.elastic;this.scrollevents=!!b.scrollevents;var c=this.snapToGrid=!!b.snapToGrid;this.maxSegments={e:1,f:1};this.currentSegment={e:0,f:0};var a=!c;if(a&&"scrollbars" in b){a=!!b.scrollbars}this._scrollTimeouts=[];this._endTimeout=null;this._barMetrics={availLength:{e:0,f:0},tipSize:0,maxOffset:{e:0,f:0},offsetRatios:{e:0,f:0},sizes:{e:0,f:0}};this._dom={outer:d};this._isScrolling={e:false,f:false,general:false};this._activeAxes=[];this._isTracking=false;this._lastEvents=[];this._maxOffset={e:0,f:0};this._metrics={offsetWidth:-1,offsetHeight:-1,scrollWidth:-1,scrollHeight:-1};this._innerSize=null;this._scrollBegan=false;this._scrollOffset=new this._Matrix();this._initDom(a)}TouchScroll.prototype={config:TouchScroll.config,_axes:["e","f"],_eventNames:TouchScroll._eventNames,_handlerNames:{touchstart:"onTouchStart",mousedown:"onTouchStart",touchmove:"onTouchMove",mousemove:"onTouchMove",touchend:"onTouchEnd",mouseup:"onTouchEnd",touchcancel:"onTouchEnd",DOMSubtreeModified:"onDOMChange",focus:"onChildFocused"},_has3d:"m11" in new WebKitCSSMatrix(),_Matrix:WebKitCSSMatrix,_scrollerTemplate:['<div class="-ts-layer -ts-outer">','<div class="-ts-layer">','<div class="-ts-layer -ts-inner"></div>',"</div>","</div>",'<div class="-ts-bars"></div>'].join(""),_scrollbarTemplate:['<div class="-ts-bar -ts-bar-e">','<div class="-ts-indicator-e">','<div class="-ts-bar-part -ts-bar-1"></div>','<div class="-ts-bar-part -ts-bar-2"></div>','<div class="-ts-bar-part -ts-bar-3"></div>',"</div>","</div>",'<div class="-ts-bar -ts-bar-f -ts-indicator-f">','<div class="-ts-bar-part -ts-bar-1"></div>','<div class="-ts-bar-part -ts-bar-2"></div>','<div class="-ts-bar-part -ts-bar-3"></div>',"</div>"].join(""),_styleSheet:TouchScroll._styleSheet,centerAt:function centerAt(d,c,b){var a=this._metrics;d+=Math.ceil(a.offsetWidth/2);c+=Math.ceil(a.offsetHeight/2);this.scrollTo(d,c,b)},handleEvent:function handleEvent(b){var a=this._handlerNames[b.type];if(a){this[a](b)}},hideScrollbars:function hideScrollbars(){var a=this._dom.bars;if(a){a.outer.className="-ts-bars"}},onChildFocused:function onChildFocused(a){var b=this._dom.scrollers.inner;var e=a.target;if(e===b){return}var d=0,c=0;do{d+=e.offsetLeft;c+=e.offsetTop;e=e.offsetParent}while(e!==b);var h=this._scrollOffset.inverse();var f=this._metrics;var g=false,j=h.e,i=h.f;var k=d>j&&d<j+f.offsetWidth;var l=c>i&&c<i+f.offsetHeight;if(!k){j=d;g=true}if(!l){i=c;g=true}if(g){this.scrollTo(d,c,100)}},onDOMChange:function onDOMChange(a){this.setupScroller()},onTouchStart:function onTouchStart(a){if(!this._isScrolling.general){return}this._stopAnimations();this.setupScroller();this._isTracking=true;this._scrollBegan=false;a=a.touches&&a.touches.length?a.touches[0]:a;this._lastEvents[1]={pageX:a.pageX,pageY:a.pageY,timeStamp:a.timeStamp}},onTouchMove:function onTouchMove(b){if(!this._isTracking){return}b.preventDefault();var i=this._lastEvents;var a=i[1];var f=b.touches&&b.touches.length?b.touches[0]:b;var e=f.pageX;var d=f.pageY;var h=new this._Matrix();h.e=e-a.pageX;h.f=d-a.pageY;var c=this._scrollBegan;if(!c){var g=this.config.threshold;this._scrollBegan=c=g<=h.e||g<=-h.e||g<=h.f||g<=-h.f;if(c){this.showScrollbars();clearTimeout(this._endScroll);var j=this._dom.scrollers.inner.parentNode;if(!j._originalClassName){j._originalClassName=j.className}j.className+=" scrolling"}}if(c){this._scrollBy(h);i[0]=a;i[1]={pageX:e,pageY:d,timeStamp:b.timeStamp}}},onTouchEnd:function onTouchEnd(o){var d=this;if(!this._isTracking||!this._scrollBegan){this._isTracking=this._scrollBegan=false;this.hideScrollbars();return}o.preventDefault();o.stopPropagation();this._isTracking=this._scrollBegan=false;var r=this._isScrolling;var n=this.config.flicking;var c=this._lastEvents;var g=c[0];var f=c[1];var l=o.timeStamp-f.timeStamp;var m=r.e?f.pageX-g.pageX:0;var k=r.f?f.pageY-g.pageY:0;var i=Math.sqrt(m*m+k*k);var q=f.timeStamp-g.timeStamp;var b=i/q;var j=l<=n.triggerThreshold&&b>=n.minSpeed;var h,a,p;if(j){h=this._computeFlick(b);p=h[0];a=h[1]}if(j&&p&&a){var e=new this._Matrix();e.e=m/i*a;e.f=k/i*a;this._flick(p,e)}else{this.snapBack()}this._lastEvents[0]=this._lastEvents[1]=null},scrollBy:function scrollBy(d,b,c){var a=this._determineOffset(true).inverse().translate(d,b,0);return this.scrollTo(a.e,a.f,c)},scrollTo:function scrollTo(g,c,d){if(d<=0){var a=this._maxOffset;g=Math.max(Math.min(g,a.e),0);c=Math.max(Math.min(c,a.f),0)}var b=this._scrollOffset.translate(g,c,0).inverse();if(d>0){this._flick(d,b)}else{this._scrollBy(b,true)}},setupScroller:function setupScroller(e){var c=this._dom;var M=c.outer;var N=M.offsetWidth;var h=M.offsetHeight;var D=M.scrollWidth;var n=M.scrollHeight;var G=this._metrics;if(!e&&N===G.offsetWidth&&h===G.offsetHeight&&D===G.scrollWidth&&n===G.scrollHeight){return false}G.offsetWidth=N;G.offsetHeight=h;G.scrollWidth=D;G.scrollHeight=n;var s=this._maxOffset={e:Math.max(D-N,0),f:Math.max(n-h,0)};var b=this._isScrolling={e:s.e>0,f:s.f>0};b.general=b.e||b.f;var p=this._activeAxes=this._axes.filter(function(i){return b[i]});var O=c.scrollers;var a=O.inner;var x=a.offsetWidth;var l=a.offsetHeight;var t=this._innerSize={e:x,f:l};this.maxSegments={e:Math.ceil(D/x),f:Math.ceil(n/l)};var j=[];var f=this._scrollOffset;var y=new this._Matrix(),v;var I=0,B=this._axes,o;while((o=B[I++])){var L=f[o];var K=-s[o];if(L>0||L<K){f[o]=L=L>0?0:K;v=y.translate(0,0,0);v[o]=L;j[j.length]={style:O[o].style,matrix:v}}}var u=c.bars;if(u){var d=true,C;I=0;while((o=B[I++])){C=u[o];C.className=C.className.replace(" active","");if(b[o]){C.className+=" active"}else{d=false}}u.outer.className=u.outer.className.replace(" -ts-bars-both","");if(d){u.outer.className+=" -ts-bars-both"}var E=this.config.scrollHandleMinSize;var k=this._barMetrics;var r=k.availLength;r.e=u.e.offsetWidth;r.f=u.f.offsetHeight;var F=k.sizes;F.e=Math.round(Math.max(r.e*N/D),E);F.f=Math.round(Math.max(r.f*h/n),E);var H=k.tipSize;var A=k.maxOffset;A.e=r.e;A.f=r.f;var q=k.offsetRatios;q.e=A.e/-D;q.f=A.f/-n;var J,g,w,z;I=0;B=p;while((o=B[I++])){J=u.parts[o];H=H||J[0].offsetHeight;g=F[o];w=g-H*2;k.maxOffset[o]=r[o]-g;z=q[o]*f[o];J[1].style.height=w+"px";j.push({style:J[3].style,matrix:{e:0,f:z}},{style:J[1].style,matrix:{e:0,f:H}},{style:J[2].style,matrix:{e:0,f:H+w}})}k.tipSize=H}this._setStyleOffset(j);return true},showScrollbars:function showScrollbars(){var a=this._dom.bars;if(a){a.outer.className+=" -ts-bars-active"}},snapBack:function snapBack(a,x,R){if(R>0){var V=this._scrollTimeouts;var J=this;V[V.length]=setTimeout(function(){J.snapBack()},R);return null}var N=a?[a]:this._activeAxes;var q=this.config.snapBack;if(typeof x==="undefined"){x=q.defaultTime}var u=q.timingFunc;var d=this._dom;var W=d.scrollers;var C=d.bars;var n=!!C;var j=this.snapToGrid;var B,E,o;if(j){B=this._innerSize;E=this.maxSegments;o=this.currentSegment}var m,Q,S,L,r;if(n){m=this._barMetrics;Q=m.sizes;S=m.tipSize;L=m.maxOffset;r=C.parts}var e=this._determineOffset(true);var K=this._maxOffset;var T=0,v;var H=new this._Matrix(),D;var F=false;var k=[],l=0;while((v=N[T++])){var M=e[v];var A=0;var h=-K[v];if(j){var p=B[v];var s=-Math.floor((M+0.5*p)/p);var b=E[v];if(s<0){s=0}else{if(s>=b){s=b-1}}h=A=-s*p;if(s!==o[v]){o[v]=s;this._fireEvent("segmentchange",{axis:v,segment:s,numSegments:b})}}if(M>=h&&M<=A){continue}F=true;D=H.translate(0,0,0);D[v]=M>A?A:h;k[l++]={style:W[v].style,matrix:D,duration:x,timingFunc:u};var z=M<h;var c=z?h-M:M-A;if(n){var g=Q[v];var G=g-2*S;var w=x;var y=u;if(c>G&&x>0){var I=I||new CubicBezier(u[0],u[1],u[2],u[3]);var O=I.getTforY(1-G/c,1/x);var P=I.getPointForT(O).x;w*=1-P;y=I.divideAtT(O)[1]}var U=r[v];var f=x-w;k[l++]={style:U[0].style,matrix:{e:0,f:0},delay:f,duration:w,timingFunc:y};k[l++]={style:U[1].style,matrix:{e:0,f:S},delay:f,duration:w,timingFunc:y};k[l++]={style:U[2].style,matrix:{e:0,f:S+G},delay:f,duration:w,timingFunc:y}}}this._setStyleOffset(k);if(F){this.showScrollbars()}else{if(!a){this._endScroll()}}return F},_computeFlick:function _computeFlick(a){var b=this.config.flicking;var e=b.friction;var d=Math.log(b.minSpeed/a)/Math.log(e);d=d>0?Math.round(d):0;var c=(1-Math.pow(e,d+1))/(1-e);var f=a*c;return[d,f]},_determineOffset:function _determineOffset(a){var g=this._dom.scrollers;var f=this._scrollOffset;var c=0,e=this._activeAxes,d;while((d=e[c++])){var b=this._getNodeOffset(g[d])[d];if(a){b=~~(b+0.5)}f[d]=b}return f},_endScroll:function _endScroll(c){clearTimeout(this._endTimeout);if(c>0){var b=this;this._endTimeout=setTimeout(function(){b._endScroll()},c)}else{var a=this._dom.scrollers.inner.parentNode;a.className=a._originalClassName;this.hideScrollbars()}},_flick:function _flick(y,ae){var U=this.config;var n=this._scrollOffset;var h=this._maxOffset;var r=this._dom;var o=r.scrollers;var C=r.bars;var O=!!C;var S,aa,J;var R=this.snapToGrid;if(R){S=this.maxSegments;aa=this.currentSegment;J=this._innerSize}var Y,j,q,L,X;if(O){Y=this._barMetrics;j=Y.offsetRatios;q=Y.tipSize;L=Y.sizes;X=C.parts}var Z=U.flicking.timingFunc;var m=new CubicBezier(Z[0],Z[1],Z[2],Z[3]);var Q=1/y;var ay=this.elastic;var ab=U.elasticity;var ap=ab.factorFlick;var F=ab.max;var ax=U.snapBack;var N=ax.alwaysDefaultTime;var av=ax.defaultTime;var p=n.multiply(ae);var aj=new this._Matrix();var al=[],e=0;var k,H;var a=0;var ai;var K=0,am=this._activeAxes,g;while((g=am[K++])){var M=ae[g];if(!M){this.snapBack(g);continue}var u=p[g];var W=-h[g];var ar=0;var ad=n[g];if(R){var ak=J[g];var d=M>0?-1:1;var au=S[g];var aw=aa[g];var x=aw+d;if(x<0){x=0}else{if(au<=x){x=au-1}}aa[g]=x;if(x===aw){this.snapBack(g);continue}else{this._fireEvent("segmentchange",{axis:g,segment:x,numSegments:au})}W=ar=-x*ak}var aq=M;if(u<W){aq=W-ad;u=W}else{if(u>ar){aq=ar-ad;u=ar}}var P=R?0:M-aq;var z=m.getTforY(aq/M,Q);if(z<0||z>1){z=0;aq=0;P=M}var T=m.divideAtT(z);var ac=T[0];var A=ac;var c=y*m.getPointForT(z).x;var B=y-c;ai=c;var ao,G;if(ay&&P){B*=ap;P*=ap;ao=P<0?-1:1;G=P*ao;if(G>F){B*=F/G;P=F*ao;G=F}}var l=aj.translate(0,0,0);l[g]=~~(u+0.5);var I=l.translate(0,0,0);I[g]+=P;var s=o[g].style;if(aq){al[e++]={style:s,matrix:l,timingFunc:ac,duration:c}}var E=X&&X[g];if(O){al[e++]={style:E[3].style,matrix:{e:0,f:~~(l[g]*j[g])},timingFunc:ac,duration:c}}if(ay&&P){ai+=B;k=[];H=0;if(O){var ah=L[g]-2*q;if(aq){al[e++]={style:E[0].style,matrix:{e:0,f:0}};al[e++]={style:E[1].style,matrix:{e:0,barTargetSize:0}};al[e++]={style:E[2].style,matrix:{e:0,f:ah+q}}}var V=B;var an=A;var at=~~(ah-G+0.5);if(at<1){at=1}if(G>ah){z=an.getTforY(ah/G,Q);var f=A.getPointForT(z).x;V*=f;an=an.divideAtT(z)[0]}var b=P<0?ah-at:0;k[H++]={style:E[0].style,matrix:{e:0,f:b},duration:V};var w=aj.translate(0,b+q,0);w.d=at/ah;k[H++]={style:E[1].style,matrix:w,duration:V};k[H++]={style:E[2].style,matrix:{e:0,f:b+q+at},duration:V}}k[H++]={style:s,matrix:I,timingFunc:A,duration:B};this._setStyleOffset(k,c);ai+=B;var ag=N?av:B;this.snapBack(g,ag,ai);ai+=ag}if(ai>a){a=ai}}this._setStyleOffset(al,0);this._endScroll(a);if(this.scrollevents&&a){var af=0;var D=this;var v=setInterval(function(){if(++af*100<a){D._fireEvent("scroll")}else{clearInterval(v)}},100)}},_fireEvent:function _fireEvent(b,a){var c=document.createEvent("Event");c.touchscroll=this;if(a){for(var d in a){c[d]=a[d]}}c.initEvent(b||"scroll",true,false);this._dom.outer.dispatchEvent(c)},_getNodeOffset:(function(){if(TouchScroll._parsesMatrixCorrectly){return function a(d){var c=window.getComputedStyle(d);return new this._Matrix(c.webkitTransform)}}var b=/matrix\(\s*-?\d+(?:\.\d+)?\s*,\s*-?\d+(?:\.\d+)?\s*,\s*-?\d+(?:\.\d+)?\s*,\s*-?\d+(?:\.\d+)?\s*\,\s*(-?\d+(?:\.\d+)?)\s*,\s*(-?\d+(?:\.\d+)?)\s*\)/;return function a(f){var d=window.getComputedStyle(f);var e=b.exec(d.webkitTransform);var c=new this._Matrix();if(e){c.e=e[0];c.f=e[1]}return c}}()),_initDom:function _initDom(c){var f=this._dom;var e=f.outer;e.className+=" TouchScroll";var d,b=document.createDocumentFragment();while((d=e.firstChild)){b.appendChild(d)}this._insertNodes(c);var a=f.scrollers.inner;a.appendChild(b);var g=this._eventNames;[g.start,g.move,g.end].forEach(function(h){e.addEventListener(h,this,false)},this);a.addEventListener("DOMSubtreeModified",this,false);a.addEventListener("focus",this,true);this.setupScroller()},_insertNodes:function _insertNodes(f){var b=this._dom;var j=b.outer;j.innerHTML=this._scrollerTemplate;var k=b.scrollers={inner:j.querySelector(".-ts-inner"),e:null,f:null};var d=j.querySelectorAll(".-ts-layer");k.f=d[0];k.e=d[1];if(f){var l=b.bars={outer:j.querySelector(".-ts-bars")};l.outer.innerHTML=this._scrollbarTemplate;var c=l.parts={};var e=0,h=this._axes,a;while((a=h[e++])){var g=l[a]=j.querySelector(".-ts-bar-"+a);c[a]=[g.querySelector(".-ts-bar-1"),g.querySelector(".-ts-bar-2"),g.querySelector(".-ts-bar-3"),l.outer.querySelector(".-ts-indicator-"+a)]}}},_scrollBy:function _scrollBy(C,h){var b=this._isScrolling;if(!b.e&&!h){C.e=0}if(!b.f&&!h){C.f=0}var z=this._maxOffset,r;var g=this._scrollOffset,F;var n=g.multiply(C),e;var u;var A=this.elastic;var D=new this._Matrix();var d=this._dom;var Q=d.scrollers;var B=d.bars;var q=!!B;var p,v,s,L,x,K;var O,a,k,j,H,G,c;if(q){p=this._barMetrics;v=B.parts;s=p.sizes;L=p.tipSize;x=p.offsetRatios;K=p.maxOffset}var m=[],o=0;var t,y;var E;var M=this.config.elasticity.factorDrag;var N=0,J=this._activeAxes,w;while((w=J[N++])){r=-z[w];F=g[w];e=n[w];E=0;if(A){F=g[w];y=F<r||F>0;if(y){e-=C[w]*(1-M)}t=e<r||e>0;var I=(y&&!t)||(t&&!y);if(I){if(F>0){e/=M}else{if(e>0){e*=M}else{if(F<r){e+=(r-F)/M}else{if(e<r){e-=(r-e)*M}}}}}if(t){E=e>0?e:e-r}}var f=e;if(e<r){e=r}else{if(e>0){e=0}}var P=D.translate(0,0,0);P[w]=e+E;m[o++]={style:Q[w].style,matrix:P};n[w]=A?f:e;if(q){O=v[w];j=~~(e*x[w]+0.5);H=K[w];if(j<0){j=0}else{if(j>H){j=H+a-k-2*L}}m[o++]={style:O[3].style,matrix:{e:0,f:j}};a=k=s[w]-2*L;var l=0;if(t){c=E<0?-E:E;k-=~~(c+0.5);if(k<1){k=1}if(E<0){l=a-k}}if(t||y){m[o++]={style:O[0].style,matrix:{e:0,f:l}}}G=D.translate(0,l+L,0);G.d=k/a;m[o++]={style:O[1].style,matrix:G};m[o++]={style:O[2].style,matrix:{e:0,f:l+k+L}}}}this._setStyleOffset(m);this._scrollOffset=n;if(this.scrollevents&&m.length){this._fireEvent("scroll")}},_setStyleOffset:function _setStyleOffset(c,g){if(g){var m=this._scrollTimeouts;m[m.length]=setTimeout(function(){_setStyleOffset(c)},g)}else{var f,b;if(this._has3d){f="translate3d(";b=", 0)"}else{f="translate(";b=")"}var a,h,l,d;var k,e=0;while((k=c[e++])){a=k.style;h=k.matrix;d=k.duration;l=k.timingFunc;if(l){l=l&&l.join?"cubic-bezier("+l.join(",")+")":l}else{l=""}a.webkitTransition=d?"-webkit-transform "+l+" "+k.duration+"ms "+(k.delay||0)+"ms":"";var j=h.d;a.webkitTransform=j&&j!==1?h:f+h.e+"px, "+h.f+"px"+b}}},_stopAnimations:function _stopAnimations(){var b=this._scrollTimeouts;for(var x=0,y=b.length;x<y;x++){clearTimeout(b[x])}b.length=0;var z=this._dom;var m=this._dom.scrollers;var p=z.bars;var o=p&&p.parts;var e=this._determineOffset();var f=this._maxOffset;var l=this._barMetrics;var n=l.offsetRatios;var a=l.tipSize;var A=l.sizes;var k=new this._Matrix();var w=this._axes,c,d,h;var u,q,r,s,g;var v=[],t=0;x=0;while((c=w[x++])){d=e[c];h=-f[c];if(d>0){e[c]=d=0}else{if(d<h){e[c]=d=h}}u={e:0,f:0};u[c]=d;v[t++]={style:m[c].style,matrix:u};if(o){q=o[c];s=A[c]-2*a;v[t++]={style:q[3].style,matrix:{e:0,f:~~(d*n[c])}};v[t++]={style:q[0].style,matrix:{e:0,f:0}};v[t++]={style:q[1].style,matrix:{e:0,f:a}};v[t++]={style:q[2].style,matrix:{e:0,f:a+s}}}}this._setStyleOffset(v)}};[["scrollTop",function(){var a=this._scrollTimeouts.length?this._determineOffset():this._scrollOffset;return -a.f},function(a){this.scrollTo(0,a)}],["scrollLeft",function(){var a=this._scrollTimeouts.length?this._determineOffset():this._scrollOffset;return -a.e},function(a){this.scrollTo(a,0)}]].forEach(function(a){TouchScroll.prototype.__defineGetter__(a[0],a[1]);TouchScroll.prototype.__defineSetter__(a[0],a[2])});["childNodes","children","firstChild","firstElementChild","innerHTML","innerText","lastChild","lastElementChild"].forEach(function(a){TouchScroll.prototype.__defineGetter__(a,function(){return this._dom.scrollers.inner[a]});TouchScroll.prototype.__defineSetter__(a,function(b){this._dom.scrollers.inner[a]=b})});["insertAdjacentElement","insertAdjacentHTML","insertAdjacentText","querySelector","querySelectorAll","addEventListener","appendChild","insertBefore","replaceChild"].forEach(function(a){TouchScroll.prototype[a]=function(){var b=this._dom.scrollers.inner;return b[a].apply(b,arguments)}});