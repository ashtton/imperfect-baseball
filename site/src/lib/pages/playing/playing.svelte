<script>
    import Admin from '$lib/pages/playing/admin.svelte'
    import Offense from '$lib/pages/playing/offense.svelte'
    import Defense from '$lib/pages/playing/defense.svelte'
    import Question from '$lib/pages/playing/question.svelte'

    import {DECISION, GAME, QUESTION_STATE, ROLE} from "$lib/store.js";
</script>

<section>
    {#if $ROLE === "ADMIN"}
        <Admin />
    {:else}
        {#if $DECISION}
            <h1 style="text-align: center">Waiting..</h1>
        {:else }
            {#if $QUESTION_STATE === null}
                <Question />
            {:else}
                {#if $ROLE === "AWAY" && $GAME.gameData.bottomInning || $ROLE === "HOME" && !$GAME.gameData.bottomInning}
                    <Defense />
                {:else}
                    <Offense />
                {/if}
            {/if}
        {/if}
    {/if}
</section>