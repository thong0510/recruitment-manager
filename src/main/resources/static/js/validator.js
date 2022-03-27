function FormValidator(formSelector, userOptions = {}) {
   const $ = document.querySelector.bind(document)
   const $$ = document.querySelectorAll.bind(document)

   const formElement = formSelector instanceof HTMLElement ? formSelector : $(formSelector)
   const formRules = {}

   // defined default options
   const defaultOptions = {
      formGroup: "form-group",
      formError: "form-error",
      errorClass: "invalid",
      validationRules: {
         required(inputName = "This field", message) {
            return function (value) {
               return value ? undefined : message ? message :`${inputName} is required`
            }
         },
         email(inputName, message) {
            const regex = /\S+@\S+\.\S+/
            return function (value) {
               return regex.test(value)
                  ? undefined
                  : message
                  ? message
                  : "Please enter a valid email address"
            }
         },
         fullname(inputName, message) {
            const regex = /^[a-z Ạ-ỹ A-Z]{1,}(?: [a-z Ạ-ỹ A-Z]+){1,10}$/
            return function (value) {
               return regex.test(value) ? undefined : message ? message : "Please enter a valid fullname"
            }
         },
         phone(inputName, message) {
            const regex = /^\d{8,12}$/
            return function (value) {
               if(value) {
                  return regex.test(value) ? undefined : message ? message : "Please enter a valid phone number"
               } else {
                  return undefined
               }
            }
         },
         minlength(length, inputName = "This field", message) {
            return function (value) {
               return value.length >= length
                  ? undefined
                  : message
                  ? message
                  : "Please enter a valid email address"`${inputName} must be at least ${length} characters long`
            }
         },
         maxlength(length, inputName = "This field", message) {
            return function (value) {
               return value.length <= length
                  ? undefined
                  : message
                  ? message
                  : "Please enter a valid email address"`${inputName} must be at most ${length} characters long`
            }
         },
         pattern(pattern, inputName = "This field", message) {
            const regex = new RegExp(pattern)
            return function (value) {
               return regex.test(value)
                  ? undefined
                  : message
                  ? message
                  : "Please enter a valid email address"`${inputName} must be match pattern: ${regex}`
            }
         },
         equalTo(originalName, matchingName = "This field", message) {
            const element = $(`input[name=${originalName}]`)
            console.log(element)
            return function (value) {
               return element.value === value
                  ? undefined
                  : message
                  ? message
                  : "Please enter a valid email address"`${matchingName} value must be match ${originalName} value`
            }
         },
      },
   }

   // throw error if userOptions is not an object
   if (!userOptions instanceof Object) throw TypeError("options must be an object")

   const options = { ...defaultOptions, ...userOptions } // merge default and user options

   ;(() => {
      // IIFE => add default style to html
      const style = document.createElement("style")
      style.innerHTML = `.${options.formGroup}.${options.errorClass} .${options.formError} {
                color: red;
                display: contents;
                margin-top: 3px;
                } `
      // prepend to head tag to ensure it can be override by user
      document.getElementsByTagName("head")[0].prepend(style)
   })()

   function capitialize(str) {
      return str[0].toUpperCase() + str.slice(1).replace("-", " ")
   }

   const inputs = formElement.querySelectorAll("[name][rules]")

   if (inputs.length > 0) {
      Array.from(inputs) // loop through form inputs to initialize form rules
         .forEach((input) => {
            const rules = input.getAttribute("rules").split("|")

            rules.forEach((rule) => {
               const ruleInfo = rule.split(":")
               const message =
                  options.errorMessages &&
                  options.errorMessages[input.name] &&
                  options.errorMessages[input.name][ruleInfo[0]]
               //
               const ruleValue =
                  ruleInfo.length === 1
                     ? options.validationRules[rule](capitialize(input.name), message)
                     : options.validationRules[ruleInfo[0]](
                          ruleInfo[1],
                          capitialize(input.name),
                          message
                       )

               if (Array.isArray(formRules[input.name])) {
                  formRules[input.name].push(ruleValue)
               } else {
                  formRules[input.name] = [ruleValue]
               }
            })
            input.removeAttribute("rules")

            input.onblur = validate // validate when blur
            input.oninput = clearError // clear error when input new value
         })

      function getParent(element, selector) {
         while (element.parentElement) {
            if (element.parentElement.matches(selector)) {
               return element.parentElement
            }
            element = element.parentElement
         }
      }

      function validate(event) {
         const formGroup = getParent(event.target, "." + options.formGroup) // query form group
         // const formError = formGroup.querySelector(options.formError) // query selector containing message

         const rules = formRules[event.target.name],
            type = event.target.type
         let hasError,
            message,
            value = event.target.value.trim()

         if (["radio", "checkbox"].includes(type) && value) {
            const inputElements = Array.from(formGroup.querySelectorAll(`input[type=${type}]`))
            value = inputElements.some((input) => input.checked)
         } else if (type.includes("select")) {
            // just need to check at least option is selected to pass validate
            // even with select-multiple
            value = event.target.querySelector("option:checked:not(:disabled)")
         }
         hasError = rules.find((rule) => rule(value)) // check if input has error

         // check if hasError is true? if true it'll return a function
         if (!!hasError) message = hasError(value) // add value and '()' after this to excute

         formGroup.classList.toggle(options.errorClass, !!hasError)

         if (message) {
            const isErrorExist = formGroup.querySelector("." + options.formError) != null
            const formError = isErrorExist
               ? formGroup.querySelector("." + options.formError)
               : document.createElement("span")
            formError.className = options.formError
            formError.innerHTML = message
            if (!isErrorExist) {
               formGroup.insertAdjacentHTML("beforeEnd", formError.outerHTML)
            }
         }

         return !!hasError
      }

      function clearError(event) {
         const formGroup = getParent(event.target, "." + options.formGroup) // query form group
         const formError = formGroup.querySelector("." + options.formError) // query selector containing message

         if (!formGroup || !formError) return

         if (formGroup.classList.contains(options.errorClass)) {
            formGroup.classList.remove(options.errorClass)
            formGroup.removeChild(formError)
         }
      }

      // handle validate when submit
      formElement.onsubmit = (event) => {
         event.preventDefault()

         let isValid = true
         // = !Array.from(inputs).find(input => validate({ target: input }))

         for (input of inputs) {
            const error = validate({ target: input })
            if (error) isValid = false
         }

         if (isValid) {
            if (typeof this.onSubmit === "function") {
               // return form input values as an object
               const allInputs = [
                  ...formElement.querySelectorAll("input"),
                  ...formElement.querySelectorAll("select"),
                  ...formElement.querySelectorAll("textarea"),
               ]
               const formValues = Array.from(allInputs).reduce((values, input) => {
                  switch (input.type) {
                     case "radio":
                        values[input.name] = formElement.querySelector(
                           `input[name=${input.name}]:checked`
                        ).value
                        break
                     case "checkbox":
                        if (!Array.isArray(values[input.name])) values[input.name] = []
                        if (!input.matches(":checked")) {
                           return values
                        }
                        values[input.name].push(input.value)
                        break
                     case "select-multiple":
                        values[input.name] = Array.from(
                           input.querySelectorAll("option:checked"),
                           (el) => el.value
                        )
                        break
                     case "file":
                        values[input.name] = input.files
                        break
                     default:
                        values[input.name] = input.value.trim()
                        break
                  }

                  return values
               }, {})

               this.onSubmit(formValues) // pass input values to submit function
            } else {
               formElement.submit()
            }
         }
      }
   }
}
