function dropdownX1() {
  return {
    options: [],
    selected: [],
    show: false,
    open() { this.show = true },
    close() { this.show = false },
    isOpen() { return this.show === true },
    select(index, event) {

      if (!this.options[index].selected) {

        this.options[index].selected = true;
        this.options[index].element = event.target;
        this.selected.push(index);
        let addInput = `<input hidden name="listRole" id="${this.options[index].text}" value="${this.options[index].value}"/>`
        document.querySelector("#roles").insertAdjacentHTML('beforeend', addInput);
      } else {
        this.selected.splice(this.selected.lastIndexOf(index), 1);
        this.options[index].selected = false
        document.getElementById(this.options[index].text).remove();
      }

    },
    remove(index, option) {
      this.options[option].selected = false;
      this.selected.splice(index, 1);
      document.getElementById(this.options[option].text).remove();
    },
    loadOptions() {
      const options = document.getElementById('select').options;
      for (let i = 0; i < options.length; i++) {
        this.options.push({
          value: options[i].value,
          text: options[i].innerText,
          selected: options[i].getAttribute('selected') != null
        });
        if (options[i].getAttribute('selected') === '') {
          this.selected.push(i);
          let addInput = `<input hidden name="listRole" id="${this.options[i].text}" value="${this.options[i].value}"/>`
          document.querySelector("#skills").insertAdjacentHTML('beforeend', addInput);
        }
      }

    },
    selectedValues() {
      return this.selected.map((option) => {
        return this.options[option].value;
      })
    }
  }
}
