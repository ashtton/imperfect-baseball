<script>
    import {QUESTION, QUESTION_STATE} from "$lib/store.js";

    let answers = [...$QUESTION.answers];
    let correct = answers[0]
    shuffleArray(answers)

    function shuffleArray(array) {
        for (let i = array.length - 1; i > 0; i--) {
            let j = Math.floor(Math.random() * (i + 1));
            let temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }

    function chooseAnswer(answer) {
        QUESTION_STATE.set(answer === correct ? "true" : "false")
    }
</script>

<section>
    <h1>{$QUESTION.question}</h1>
    <div class="answers">
        {#each answers as answer}
            <div class="answer" on:click={() => chooseAnswer(answer)}>{answer}</div>
        {/each}
    </div>
</section>

<style>
    h1 {
        text-align: center;
        font-size: 2rem;
    }
    .answers {
        display: grid;
        gap: 1rem;
        color: black;
    }

    .answer {
        border-radius: 0.5rem;
        background-color: gray;
        padding: 0.5rem;
        cursor: pointer;
    }

    .answer:hover {
        background-color: lightgray;
    }
</style>