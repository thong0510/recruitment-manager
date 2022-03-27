const BASE_URL = "http://localhost:8080/api/v1/";

const $ = document.querySelector.bind(document)
const $$ = document.querySelectorAll.bind(document)

const dataElement = $("#container")

const quizId = dataElement.dataset.quizid || 160 // should get from an html attribute tag
console.log(quizId)
const numberOfQuestions = dataElement.dataset.numberofquestions || 2// should get from an html attribute tag
const level = dataElement.dataset.level || null// should get from an html attribute tag
const userId = dataElement.dataset.userid || 17// should get from an html attribute tag

const app = {
    timerId: null,
    timeDoQuiz: 0,
    questions: [],
    currentIndex: 0,
    currentIndexView: 0,
    configs: {
        timeCount: 30, // default time count for each answer is 30 seconds
    },
    loadData: async () => {

        try {
            const response = await axios({
                method: "POST",
                url: BASE_URL + `questions/${quizId}/all`,
                headers: {
                    "Access-Control-Allow-Origin": "*",
                    "Content-Type": "application/json",
                },
                data: {
                    tagId: null,
                    level: level,
                    pagination: { page: 0, limit: numberOfQuestions, sort: null },
                },
            })

            const data = response.data
            console.log(data)
            app.questions = data.content

            function shuffle(array) {
                let currentIndex = array.length,
                    randomIndex

                // While there remain elements to shuffle...
                while (currentIndex != 0) {
                    // Pick a remaining element...
                    randomIndex = Math.floor(Math.random() * currentIndex)
                    console.log('randomIndex' + randomIndex);
                    console.log('randomIndex' + currentIndex);
                    currentIndex--

                        // And swap it with the current element.
                        ;[array[currentIndex], array[randomIndex]] = [
                            array[randomIndex],
                            array[currentIndex],
                        ]
                }

                return array
            }

            shuffle(app.questions) // shuffle questions to get random order in every play time
        } catch (error) {
            if (error.response) {
                console.log(error.response.data)
                console.log(error.response.status)
                console.log(error.response.headers)
            } else if (error.request) {
                console.log(error.request)
            } else {
                console.log("Error", error.message)
            }
            console.log(error.config)
        }
    },
    render(index = this.currentIndex) {
        $("#title").innerHTML = `${this.questions[index].title} ${this.questions[index].isMultiple ? ' (multiple answer)' : ''}`
        $("#answers").innerHTML = this.questions[index].answers.map(
            (answer) =>
                `<div data-index=${answer.id} class="answer lg:w-5/12 w-10/12 mb-5 mx-auto h-20  ${answer.checked ? "bg-red-600 hover:bg-red-500" : "bg-blue-600 hover:bg-blue-500"
                } rounded-3xl flex justify-center items-center cursor-pointer ">
                <label class="text-xl text-white font-semibold cursor-pointer">${answer.text
                }</label>
            </div>`
        )
        $(".question-count").innerHTML = `${index + 1}/${this.questions.length}`
    },
    renderView(index = this.currentIndexView) {
        const data = this.calculateResult();
        $("#title-review").innerHTML = data.results[index].question
        $("#question-review").innerHTML = `Question ${index + 1}:  `
        $("#answers-review").innerHTML = data.results[index].answer.map(
            (answer) =>
                `<div class="indicator bg-gray-100 w-full p-5 rounded-xl mb-5">
                                <span class="indicator-item badge ${answer.checked ? answer.isCorrect ? 'badge-success' : 'badge-error' : 'hidden'} p-2 mr-8">Your Answer</span>
                                <div class="bg-gray-100 w-full rounded-xl">
                                    <div class="form-control">
                                        <label class="cursor-pointer label">
                                            <span class="label-text">${answer.text}</span>
                                            <input type="checkbox" ${answer.isCorrect ? "checked='checked'" : ""} class="checkbox checkbox-accent rounded-3xl" onclick="return false;">
                                        </label>
                                    </div>
                                </div>
                            </div>`
        ).join('')
    },
    calculateResult() {
        let count = 0
        const resultArray = []
        this.questions.forEach((question) => {
            const result = {
                question: question.title,
                answer: question.answers,
                userAnswers: question.answers
                    .filter((answer) => answer.checked)
                    .map((answer) => answer.text),
                correctAnswers: question.answers
                    .filter((answer) => answer.isCorrect)
                    .map((answer) => answer.text),
            }

            resultArray.push(result)

            const isCorrect = question.answers
                .filter((answer) => answer.isCorrect)
                .every((answer) => answer.checked)
            if (isCorrect) count++
        })

        return {
            correct: `${count}`,
            results: resultArray,
        }
    },

    async start(time = this.questions.length * this.configs.timeCount) {
        // initialize minute and second value calculated by time paramater
        let s = time % 60,
            m = Math.floor(time / 60) >= 1 ? Math.floor(time / 60) : 0

        const renderTime = () => {
            const minute = ("" + m).length < 2 ? "0" + m : m
            const second = ("" + s).length < 2 ? "0" + s : s
            $("#timer").innerHTML = minute + ":" + second
        }
        renderTime()
        let count = 0;
        app.timeDoQuiz = 0;
        this.timerId = setInterval(function () {
            if (m == 0 && s == 0) {
                $("#timer").innerHTML = "EXPIRED"
                $("#finish").innerHTML = "View Results"
                count = count + 1;
                if (count == 1) {
                    modalClose('main-modal')
                    $('#finish').click();
                }
                stop()
                return
            } else if (s == 0) {
                s = 59
                m--
            } else {
                s--
            }
            app.timeDoQuiz++;
            renderTime()
        }, 1000)
    },
    stop() {

        clearInterval(this.timerId)
        modalClose('main-modal')
        $("#timer").innerHTML = "EXPIRED"
        $("#finish").innerHTML = "View Results"

        // do the same thing with handleEvents().finish function
        console.log(this.calculateResult())
    },
    handleEvents() {
        $(".prev-btn").addEventListener("click", () => {
            if (this.currentIndex == 0) return
            this.render(--this.currentIndex)
        })

        $(".next-btn").addEventListener("click", () => {
            if (this.currentIndex == this.questions.length - 1) return
            this.render(++this.currentIndex)
        })

        $("#prev-btn-review").addEventListener("click", () => {
            if (this.currentIndexView == 0) return
            this.renderView(--this.currentIndexView)
        })

        $("#next-btn-review").addEventListener("click", () => {
            if (this.currentIndexView == this.questions.length - 1) return
            this.renderView(++this.currentIndexView)
        })

        // event on click to choose answer
        $("#answers").addEventListener("click", (e) => {
            const target = e.target.closest(".answer") // get answer element inside the container
            if (target == null) return
            const selectedAnswerId = target.dataset.index // we stored id of each answer using data-* attribute tag in HTMl

            const question = this.questions[this.currentIndex]

            // check if this question is multiple choice or not
            const multiple = question.isMultiple

            if (!multiple) {
                // handle selection when multiple is false

                // set checked for each answer to false if that answer id not match the id of answer we clicked
                question.answers.forEach((element) => {
                    element.checked = element.id == selectedAnswerId
                })

                // iterate all annswers and modify its class based on its answer.checked property
                $$(".answer").forEach((element, index) => {
                    const answer = question.answers[index]
                    if (answer.checked) {
                        element.classList.add("bg-red-600", "hover:bg-red-500")
                        element.classList.remove("bg-blue-600", "hover:bg-blue-500")
                    } else {
                        element.classList.remove("bg-red-600", "hover:bg-red-500")
                        element.classList.add("bg-blue-600", "hover:bg-blue-500")
                    }
                })
            } else {
                // handle selection when multiple is true

                // find the current selected answer on click
                const selectedAnswer = question.answers.find(
                    (element) => element.id == selectedAnswerId
                )
                // reverse the checked property
                // if it's null (have not chosen yet) => true, true => false, false => true
                selectedAnswer.checked = !selectedAnswer.checked

                // modify the target element class based on its answer.checked property
                if (selectedAnswer.checked) {
                    target.classList.add("bg-red-600", "hover:bg-red-500")
                    target.classList.remove("bg-blue-600", "hover:bg-blue-500")
                } else {
                    target.classList.remove("bg-red-600", "hover:bg-red-500")
                    target.classList.add("bg-blue-600", "hover:bg-blue-500")
                }
            }
        })

        $("#finish").addEventListener("click", () => {
            // TODO: using Modal-like component of Tailwind/DaisyUI to open confirmation pop-up
            // only if user click "OK" button then do the following function code

            // we got result of the quiz using calculate result function, it returns an object
            // then we should display its result in view by modify DOM element to remove all do quiz component
            // and replace it with result component
                
            if ($("#timer").textContent !== "EXPIRED") {
                openModal('main-modal')
            } else {
                openModal('result-modal')
                const data = this.calculateResult();
                let numberOfQuestions = data.results.length;
                let correct = data.correct;
                let attempt = 0;
                data.results.forEach((question) => {
                    if (question.userAnswers.length > 0) {
                        attempt = attempt + 1;
                    }
                })
                $("#number-question").textContent = numberOfQuestions;
                $("#answer-correct").textContent = correct;
                $("#answer-wrong").textContent = numberOfQuestions - correct;
                $("#answer-attempt").textContent = attempt;

                $("#score").textContent = (correct / numberOfQuestions) * 100 || 0;
                let time = app.timeDoQuiz;
                let s = time % 60,
                    m = Math.floor(time / 60) >= 1 ? Math.floor(time / 60) : 0

                const minute = ("" + m).length < 2 ? "0" + m : m
                const second = ("" + s).length < 2 ? "0" + s : s
                $("#time").innerHTML = minute + ":" + second

                this.renderView()
                testApi(correct, numberOfQuestions, attempt);

            }
            
        }),
        $("#view-details").addEventListener("click", () => {
            openModal('view-modal')
        })
        $("#start-again").addEventListener("click", () => {
            setTimeout(function () {
                location.reload()
            }, 200);
        })
        $("#downloadBtn").addEventListener("click", () => {
            download(`http://localhost:8080/quiz/result/${quizId}/${userId}`);
        })
    }
    ,
    run() {
        this.loadData().then(() => {
            this.render()
            this.handleEvents()
            this.renderView()
            this.start()
        })
    },
}

// app.run()

function exits() {
    window.location.href = "http://localhost:8080/quiz/detail/" + quizId
}

openModal('count-down-modal');

setTimeout(function () {
    modalClose('count-down-modal')
    app.run()

}, 3500);

async function testApi(correct, numberOfQuestions, attempt) {
    const maxScore = (correct / numberOfQuestions) * 100 || 0;
    const recentActiveDate = new Date();
    let obj = {
        id: {
            userId: userId,
            quizId: quizId,
        },
        maxScore: maxScore,
        remainingTime: app.timeDoQuiz,
        recentActiveDate: recentActiveDate,
        attempt: attempt,
        correctAnswers: correct,
        wrongAnswers: numberOfQuestions - correct
    }

    const pages = "http://localhost:8080/api/v1/do-quiz/create";

    let finish;

    finish = await postOrPut('POST', pages, obj);
    
}

function viewResult(form, key) {
    app.stop();
    $("#" + key).click();
}

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

async function postOrPut(method, url, data) {
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

        return true;
    } catch (err) {
        return false;
    }
}
