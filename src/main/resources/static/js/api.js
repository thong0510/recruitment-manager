async function postOrPut(method,url, data) {
    try {
       // Create request to api service
       const req = await fetch(url, {
          method: method,
          headers: {
             'Content-Type': 'application/json'
          },
 
          // format the data
          body: JSON.stringify(data),
       });
 
       const res = await req.json();

        alert("Create successfully");
      return true;
    } catch (err) {
       alert(`ERROR: ${err}`);
       return false;
    }
 }

 async function fetchApi(method ,url) {

    const response = await fetch(url, {
         method: method,
            contentType: 'application/json',
            headers: {
                'Authorization': `Bearer ${getCookie('Authorization')}`,
            },
    });
    if (!response.ok) {
      const message = `An error has occured: ${response.status}`;
      alert(message);
    }
    return response;
  }

function getCookie(cname) {
    let name = cname + "=";
    let decodedCookie = decodeURIComponent(document.cookie);
    let ca = decodedCookie.split(';');
    for(let i = 0; i <ca.length; i++) {
        let c = ca[i];
        while (c.charAt(0) == ' ') {
            c = c.substring(1);
        }
        if (c.indexOf(name) == 0) {
            return c.substring(name.length, c.length);
        }
    }
    return "";
}

  