<template>
  <div class="intro-grid">
    <section class="info-block">
      <h1>Find Your Linux Distro</h1>
      <p>
        Why create another take-a-quiz-to-get-your-linux-distro website? Because its an interesting project i wanted to do.
        This small website can help you, if you want to finally switch to Linux from Windows, or need an OS for your server.
        Don't forget to like the distro you've chosen after taking the quiz!
      </p>
      <p>
        This app wouldn't be possible without Distrowatch, go check them out! I also included links to other projects i found interesting.
      </p>
    </section>
    
    <section class="action-block">
      <h3>Ready to start?</h3>
      <p>The quiz takes about 2 minutes.</p>
      <button @click="$emit('start')" class="btn-start">Start the Quiz</button>
    </section>
  </div>
</template>

<style scoped>
.intro-grid {
  display: grid;
  gap: 2rem;
  margin-top: 2rem;
}
.info-block {
  background: var(--white);
  padding: 2.5rem;
  border-radius: 12px;
  box-shadow: var(--shadow);
}
.action-block {
  background: var(--primary);
  color: white;
  padding: 2rem;
  border-radius: 12px;
  text-align: center;
}
.btn-start {
  background: var(--white);
  color: var(--primary);
  padding: 1rem 2rem;
  border-radius: 8px;
  font-size: 1.1rem;
  margin-top: 1rem;
}
.btn-start:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(0,0,0,0.2);
}
</style>