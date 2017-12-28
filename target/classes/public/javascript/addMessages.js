/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
function addMessages(id) {
    /*<![CDATA[*/
    console.log("addMessages");
    var domelement = document.getElementById(id);
    var tnode = document.createElement("tnode");
    
    var messageList = /*[[${"messages"}]]*/ [("title1", "content1")];
    console.log("messageList", messageList);
    var tr = document.createElement("tr");
    var td = document.createElement("td");
    td.setAttribute("colspan", 3);    
    if (messageList.length === 0) {
        console.log("No messages");
        var para = document.creatElement("p");
        var textContent = document.createTextNode("No messages yet.");        
        para.appendChild(textContent);
        tr.appendChild(para);
        tnode.appendChild(tr);
    } else {
        console.log("# Messages:", messageList.length);
        var sep = document.createElement("td");
        var textContent = document.createTextNode(" | ");
        var atitle = document.createElement("td");
        var titleContent = document.createTextNode("");
        atitle.appendChild(titleContent);
        var aMessage = document.createElement("td");
        var messageContent = document.createTextNode("");
        aMessage.appendChild(messageContent);
        tr.appendChild(atitle);
        tr.appendChild(sep);
        tr.appendChild(aMessage);
        console.log("tr:", tr);
        for (i = 0; i < messageList.length; i++) {
           titleContent.nodeValue = messageList[i].title;
           messageContent.nodeValue=messageList[i].content;
           var onetr = tr.cloneNode(true);
           console.log("onetr", onetr);
           tnode.appendChild(onetr);
        }
    }
    domelement.appendChild(tnode);
    console.log("@exit: domelement", domelement);
    /*]]>*/
}

