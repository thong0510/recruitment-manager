function paramFilters(param, value, firstSearch = false){
  let uri = new URL(document.baseURI);
  uri.searchParams.set(param, value);
  if(uri.searchParams.has("gender") && firstSearch === true) uri.searchParams.set("page", 1);
  if(uri.searchParams.has("status") && firstSearch === true) uri.searchParams.set("page", 1);
  if(uri.searchParams.has("search") && firstSearch === true) uri.searchParams.set("page", 1);
  if(value.length == 0) uri.searchParams.delete(param);
  window.location.href = uri.href;
}

function showPreviewImage(event) {
  if (event.target.files.length > 0) {
    var preview = document.getElementById("preview-image");
    var src = URL.createObjectURL(event.target.files[0]);
    preview.src = src;
  }
}

function openModal(key, id) {
  if (id !== null) candidateId = id;
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

function openModal1(key, id) {
  if (id !== null) majorId = id;
  document.getElementById(key).showModal();
  document.body.setAttribute('style', 'overflow: hidden;');
  document.getElementById(key).children[0].scrollTop = 0;
  document.getElementById(key).children[0].classList.remove('opacity-0');
  document.getElementById(key).children[0].classList.add('opacity-100')
}

function openModal2(key, id) {
  if (id !== null) majorDetailId = id;
  document.getElementById(key).showModal();
  document.body.setAttribute('style', 'overflow: hidden;');
  document.getElementById(key).children[0].scrollTop = 0;
  document.getElementById(key).children[0].classList.remove('opacity-0');
  document.getElementById(key).children[0].classList.add('opacity-100')
}


function openModal3(key, id) {
  if (id !== null) skillId = id;
  document.getElementById(key).showModal();
  document.body.setAttribute('style', 'overflow: hidden;');
  document.getElementById(key).children[0].scrollTop = 0;
  document.getElementById(key).children[0].classList.remove('opacity-0');
  document.getElementById(key).children[0].classList.add('opacity-100')
}

function openModal4(key, id) {
  if (id !== null)  vacanciesId = id;
  document.getElementById(key).showModal();
  document.body.setAttribute('style', 'overflow: hidden;');
  document.getElementById(key).children[0].scrollTop = 0;
  document.getElementById(key).children[0].classList.remove('opacity-0');
  document.getElementById(key).children[0].classList.add('opacity-100')
}
