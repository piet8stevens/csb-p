/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
function executeJS(id)
{
  console.log("executeJS");
  var domelement = document.getElementById(id);
  console.log("domelement:", domelement);
  var scripts = [];

  var ret = domelement.childNodes;
  
  console.log("ret:", ret, ' length:', ret.length);
  var l = ret.length;
  for ( var i = 0; i<l; i++ ) {
        console.log("ret[i]", i," | ", ret[i]);
        if ( isScript(ret[i] ) ) {
          console.log("Push parentNode:", ret[i].parentNode);
          scripts.push( ret[i].parentNode ? ret[i].parentNode.removeChild( ret[i] ) : ret[i] );
      } else {
          executeJSElem(ret[i]);
          console.log("after executeJSElem:length:",ret.length," i:",i);
      }
  }
  console.log("scripts:", scripts);
  for(var script in scripts)
  {
    console.log("script:", script);
    evalScript(scripts[script]);
  }
}
function executeJSElem(domelement)
{
  console.log("executeJSElem:", domelement);

  var scripts = [];

  var ret = domelement.childNodes;
  console.log("ret:", ret);
  if (ret.length !== 0) {
    console.log("Length > 0!");
    for ( var i = 0; i<ret.length; i++ ) {
        console.log("ret[i]", i," | ", ret[i]);
        if ( isScript( ret[i] ) ) {
            console.log("Push:", ret[i]);
            var  s= ret[i].data.replace(/<script>/,"");
            s = s.replace(/<\/script>/,"");
            scripts.push( s );
      } else {
            executeJSElem(ret[i]);
            console.log("after executeJSElem:length:",ret.length," i:",i);
      }
    }
    console.log("scripts:", scripts);
    for(var script in scripts)
    {
      console.log("script:", scripts[script]);
      eval(scripts[script]);
    }
  }
}
function isScript( elem ) {
    if (!elem.length) { 
        return false; 
    } else {
        var res = elem.data.match(/^ +<script>/);
        return res && (res.length !== 0);
    }
}


