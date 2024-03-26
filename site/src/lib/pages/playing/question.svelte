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
</script>

<section>
    <h1>{$QUESTION.question}</h1>
    {#each answers as answer}
        <button on:click={() => {
            QUESTION_STATE.set(answer === correct ? "true" : "false")
        }}>{answer} {answer === correct ? '.' : '-'}</button>
    {/each}
</section>