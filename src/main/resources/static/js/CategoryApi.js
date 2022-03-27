let count = 1;

async function postOrPut(method, url, data) {
    try {
        // Create request to api service
        const req = await fetch(url, {
            method: method,
            headers: {
                'Content-Type': 'application/json',
                // 'Authorization': `Bearer ${getCookie('Authorization')}`,
                'Access-Control-Allow-Credentials': 'true'
            },

            // format the data
            body: JSON.stringify(data),
        });

        return await req.json();
    } catch (err) {
        alert(`ERROR: ${err}`);
        return false;
    }
}

async function callApiWithAuthenticate(method, url, data) {
    try {
        // Create request to api service
        return await fetch(url, {
            method: method,
            headers: {
                // 'Authorization': `Bearer ${getTokenByHeader().authorization}`,
            },

            // format the data
            body: JSON.stringify(data),
        });
    } catch (err) {
        alert(`ERROR: ${err}`);
        return false;
    }
}

async function get(method, url) {
    try {
        // Create request to api service
        const req = await fetch(url, {
            method: method,
            headers: {
                'Content-Type': 'application/json',
                // 'Authorization': `Bearer ${getCookie('Authorization')}`,
                'Access-Control-Allow-Credentials': 'true'
            },
        });

        return await req.json();
    } catch (err) {
        alert(`ERROR: ${err}`);
        return false;
    }
}

var previousPage = 1;
var first = 1;
var currentPages = 1;

async function previousPageNumber() {

    if (currentPages > 1) {
        await paginationByNumberPages(--currentPages);
    }
}

async function nextPage() {
    let check = document.querySelectorAll(".totalPages").length;
    if (currentPages < check) {
        await paginationByNumberPages(++currentPages);
    }
}

var keySearch = '';

async function paramFilters(param, value) {

    keySearch = value;
    currentPages = 1;
    let obj = {
        name: value,
        pagination: {
            page: 0,
            limit: 4,
            sort: {
                criteria: 'id',
                asc: true
            }
        }
    }

    const pages = "http://localhost:8080/api/v1/categories";
    let totalPages;
    let totalElements;
    let numberOfElements;

    let finish;

    finish = await postOrPut('POST', pages, obj);

    totalPages = finish.totalPages;
    totalElements = finish.totalElements;
    numberOfElements = finish.numberOfElements;

    const array = finish.content;

    let listCategory = '';
    let listPagination = '';

    array.forEach(category => {

        listCategory +=
            `<tr class="totalElements">
                                    <th>
                                        <div class="avatar">
                                            <div class="rounded-btn w-16 h-16">
                                                <img src="${category.image}" id="image${category.id}" alt="">
                                            </div>
                                        </div>
                                    </th>
                                    <td class="text-2xl" id="name${category.id}">${category.name}</td>
                                    <td>
                                        <button class="btn btn-sm" onclick="openFormEdit(${category.id})">Edit</button>
                                    </td>
                                    <td>
                                        <button onclick="deleteCategory(${category.id})" class="btn btn-outline btn-circle btn-xs flex items-center ml-3">
                                            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" class="inline-block w-4 h-4 stroke-current">
                                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12"></path>
                                            </svg>
                                        </button>
                                    </td>
                                </tr>̵`;
    })

    listPagination = `<a class="btn btn-sm btn-outline" onclick="previousPageNumber();">Previous</a>`;
    if (array.length >= 1) {
        listPagination += `<div class="btn btn-sm btn-outline totalPages bg-blue-500 border-black text-white" id="page1" onclick="paginationByNumberPages(1)" >1</div>`;
    }
    for (let i = 2; i <= totalPages; i++) {
        listPagination += `<div class="btn btn-sm btn-outline totalPages" id="page${i}" onclick="paginationByNumberPages(${i})">${i}</div>`;
    }

    listPagination += `<a class="btn btn-sm btn-outline addPagination" onclick="nextPage();">Next</a>`;

    previousPage = 1;
    document.querySelector("#pagination-category").innerHTML = listCategory;
    document.querySelector("#pagination").innerHTML = listPagination;

}

async function paginationByNumberPages(item) {

    currentPages = item;
    if (first === 1 || item !== previousPage) {
        const element2 = document.querySelector('#page' + previousPage);
        element2.classList.remove("bg-blue-500");
        element2.classList.remove("border-black");
        element2.classList.remove("text-white");
    }
    first++;

    console.log(item);
    let element1 = document.querySelector('#page' + item);
    element1.classList.add("bg-blue-500");
    element1.classList.add("border-black");
    element1.classList.add("text-white");

    console.log(previousPage);


    let obj = {
        name: keySearch,
        pagination: {
            page: item - 1,
            limit: 4,
            sort: {
                criteria: 'id',
                asc: true
            }
        }
    }

    const pages = "http://localhost:8080/api/v1/categories";
    let totalPages;
    let totalElements;
    let numberOfElements;

    let finish;

    finish = await postOrPut('POST', pages, obj);

    totalPages = finish.totalPages;
    totalElements = finish.totalElements;
    numberOfElements = finish.numberOfElements;

    if (totalPages < currentPages) {
        obj = {
            name: keySearch,
            pagination: {
                page: item - 2,
                limit: 4,
                sort: {
                    criteria: 'id',
                    asc: true
                }
            }
        }
        finish = await postOrPut('POST', pages, obj);
        totalElements = finish.totalElements;
        numberOfElements = finish.numberOfElements;

    }


    const array = finish.content;

    let listCategory = '';
    array.forEach(category => {

        listCategory +=
            `<tr class="totalElements">
                                    <th>
                                        <div class="avatar">
                                            <div class="rounded-btn w-16 h-16">
                                                <img src="${category.image}" id="image${category.id}" alt="">
                                            </div>
                                        </div>
                                    </th>
                                    <td class="text-2xl" id="name${category.id}">${category.name}</td>
                                    <td>
                                        <button class="btn btn-sm" onclick="openFormEdit(${category.id})">Edit</button>
                                    </td>
                                    <td>
                                        <button onclick="deleteCategory(${category.id})" class="btn btn-outline btn-circle btn-xs flex items-center ml-3">
                                            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" class="inline-block w-4 h-4 stroke-current">
                                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12"></path>
                                            </svg>
                                        </button>
                                    </td>
                                </tr>̵`;
    })

    previousPage = item;
    document.querySelector("#pagination-category").innerHTML = listCategory;
    let check = document.querySelectorAll(".totalPages").length;

    if (Math.ceil(totalElements / numberOfElements) < check) {
        document.querySelector("#page" + check).remove();
        element1 = document.querySelector('#page' + totalPages);
        element1.classList.add("bg-blue-500");
        element1.classList.add("border-black");
        element1.classList.add("text-white");
        previousPage = totalPages;
    }

}

var formCreateCategory = document.getElementById("create-category-form");
var formEditCategory = document.getElementById("edit-category-form");

formCreateCategory.onsubmit = async (e) => {
    e.preventDefault();

    let element = document.querySelector('#closeCreate');
    element.classList.add("loading");

    const form = e.currentTarget;
    const url = "http://localhost:8080/api/v1/categories/create";
    // const urlRefreshToken = `http://localhost:8080/api/auth/refresh-token/${getTokenByHeader().refreshtoken}`;

    let check = document.querySelectorAll(".totalElements").length;

    let obj = {
        name: '',
        pagination: {
            page: count,
            limit: 4,
            sort: {
                criteria: 'id',
                asc: true
            }
        }
    }

    const pages = "http://localhost:8080/api/v1/categories";
    let totalPages;
    let totalElements;
    let numberOfElements;

    let finish = await postOrPut('POST', pages, obj).then(
        response => {
            console.log(response)
            totalPages = response.totalPages;
            totalElements = response.totalElements;
            numberOfElements = response.numberOfElements;
        });

    const myHeaders = new Headers();
    // myHeaders.append('Authorization', `Bearer ${getTokenByHeader().authorization}`);
    try {
        const formData = new FormData(form);
        const response = await fetch(url, {
            method: 'POST',
            headers: myHeaders,
            body: formData
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
                method: 'POST',
                headers: newHeader,
                body: formData
            });
            data = await responseAgain.json();
        }

        totalElements++;
        if (check === numberOfElements) {
            if (totalElements > (totalPages * numberOfElements)) {
                totalPages++;
                let addPagination = `<a class="btn btn-sm btn-outline totalPages" id="page${totalPages}"
                    onclick="paginationByNumberPages(${totalPages})">${totalPages}</a>`
                document.querySelector(".addPagination").insertAdjacentHTML('beforebegin', addPagination);
            }
        } else {
            addCategory(data);
        }

        document.getElementById("preview-image").src = 'https://mntech.com.vn/common/images/noimg64.png';

        form.reset();
        element.classList.remove("loading");
        modalClose('main-modal');

    } catch (error) {
        console.error(error);
    }

}

var urlEditCategory;

function openFormEdit(value) {
    console.log(value);
    urlEditCategory = "http://localhost:8080/api/v1/categories/" + value;

    let name = document.querySelector("#name" + value).textContent;
    let image = document.querySelector("#image" + value).src;

    document.querySelector('#editId').setAttribute('value', value);
    document.querySelector('#preview').setAttribute('src', image);
    document.querySelector('#editName').setAttribute('value', name);

    openModal('edit-modal');
}

async function deleteCategory(value) {
    console.log(value);
    let url = 'http://localhost:8080/api/v1/categories/' + value;
    let data = await callApiWithAuthenticate('DELETE', url, null);
    console.log(data);
    await paginationByNumberPages(currentPages);

}

formEditCategory.onsubmit = async (e) => {
    e.preventDefault();

    let element = document.querySelector('#closeEdit');
    element.classList.add("loading");

    const form = e.currentTarget;

    // const urlRefreshToken = `http://localhost:8080/api/auth/refresh-token/${getTokenByHeader().refreshtoken}`;

    const myHeaders = new Headers();
    // myHeaders.append('Authorization', `Bearer ${getTokenByHeader().authorization}`);
    try {
        const formData = new FormData(form);
        const response = await fetch(urlEditCategory, {
            method: 'PUT',
            headers: myHeaders,
            body: formData
        });

        let data = await response.json();

        document.querySelector('#name' + data.id).textContent = data.name;
        document.querySelector('#image' + data.id).src = data.image;

        form.reset();
        element.classList.remove("loading");
        modalClose('edit-modal');

    } catch (error) {
        console.error(error);
    }

}

function addCategory(data) {
    let addCategory = `<tr class="totalElements">
                                    <th>
                                        <div class="avatar">
                                            <div class="rounded-btn w-16 h-16">
                                                <img src="${data.image}" id="image${data.id}"/>
                                            </div>
                                        </div>
                                    </th>
                                    <td class="text-2xl" id="name${data.id}">${data.name}</td>
                                    <td>
                                        <button class="btn btn-sm" onclick="openFormEdit(${data.id})">Edit</button>
                                    </td>
                                    <td>
                                        <button type="submit"  onclick="deleteCategory(${data.id})" class="btn btn-outline btn-circle btn-xs flex items-center ml-3">
                                            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" class="inline-block w-4 h-4 stroke-current">
                                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12"></path>
                                            </svg>
                                        </button>
                                    </td>
                                </tr>`;

    document.querySelector("#pagination-category").insertAdjacentHTML('beforeend', addCategory);
}

async function fetchApi(method, url) {

    const response = await fetch(url, {
        method: method,
        contentType: 'application/json',
        headers: {
            'Authorization': `Bearer ${getCookie('Authorization')}`,
            'Access-Control-Allow-Credentials': 'true'
        },
    });
    if (!response.ok) {
        const message = `An error has occured: ${response.status}`;
        alert(message);
    }
    return response;
}

// function getTokenByHeader() {
//     const req = new XMLHttpRequest();
//     req.open('GET', 'http://localhost:8080/quizsystem_war/', false);
//     req.send(null);
//     let arr = getResponseHeaderMap(req);
//
//     function getResponseHeaderMap(xhr) {
//         const headers = {};
//         xhr.getAllResponseHeaders()
//             .trim()
//             .split(/[\r\n]+/)
//             .map(value => value.split(/: /))
//             .forEach(keyValue => {
//                 headers[keyValue[0].trim()] = keyValue[1].trim();
//             });
//         return headers;
//     }
//     return arr;
// }

function getCookie(cname) {
    let name = cname + "=";
    let decodedCookie = decodeURIComponent(document.cookie);
    let ca = decodedCookie.split(';');
    for (let i = 0; i < ca.length; i++) {
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

// function setCookie(cname, cvalue, exdays) {
//     const d = new Date();
//     d.setTime(d.getTime() + (exdays * 24 * 60 * 60 * 1000));
//     let expires = "expires=" + d.toUTCString();
//     document.cookie = cname + "=" + cvalue + ";" + expires + ";path=/quizsystem_war";
// }

function openModal(key) {
    document.getElementById(key).showModal();
    document.body.setAttribute('style', 'overflow: hidden;');
    document.getElementById(key).children[0].scrollTop = 0;
    document.getElementById(key).children[0].classList.remove('opacity-0');
    document.getElementById(key).children[0].classList.add('opacity-100')
}

function modalClose(key) {
    document.getElementById(key).children[0].classList.remove('opacity-100');
    document.getElementById(key).children[0].classList.add('opacity-0');
    setTimeout(function () {
        document.getElementById(key).close();
        document.body.removeAttribute('style');
    }, 100);
}
