function getTokenByHeader() {
    const req = new XMLHttpRequest();
    req.open('GET', 'http://localhost:8080/quizsystem_war/', false);
    req.send(null);
    let arr = getResponseHeaderMap(req);

    function getResponseHeaderMap(xhr) {
        const headers = {};
        xhr.getAllResponseHeaders()
            .trim()
            .split(/[\r\n]+/)
            .map(value => value.split(/: /))
            .forEach(keyValue => {
                headers[keyValue[0].trim()] = keyValue[1].trim();
            });
        return headers;
    }
    return arr;
}

async function changeStatusQuiz(id,selectedInput){

    const url = `http://localhost:8081/api/v1/quizzes/${id}/${selectedInput.value}`;
    const urlRefreshToken = `http://localhost:8081/api/auth/refresh-token/${getTokenByHeader().refreshtoken}`;

    const myHeaders = new Headers();
    myHeaders.append('Authorization', `Bearer ${getTokenByHeader().authorization}`);
    try {
        const response = await fetch(url, {
            method: 'PATCH',
            headers: myHeaders,
            body: null,
        });

        let data = await response.json();

        if (data.id === undefined) {
            const callToken = await fetch(urlRefreshToken, {
                method: 'GET'
            });
            let result = await callToken.json();

            const newHeader = new Headers();
            newHeader.append('Authorization', 'Bearer ' + result.accessToken);
            setCookie('Authorization', result.accessToken, 1);
            setCookie('RefreshToken', result.refreshToken, 1);
            const responseAgain = await fetch(url, {
                method: 'PATCH',
                headers: newHeader,
                body: null
            });
            data = await responseAgain.json();
        }

    } catch (error) {
        console.error(error);
    }
}
