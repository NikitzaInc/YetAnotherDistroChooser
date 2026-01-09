<template>
  <div id="app">
    <TheHeader @navigate="handleNavigation" />
    
    <main class="container">
      <QuizIntro v-if="step === 'intro'" @start="step = 'quiz'" />
      
      <QuizCard 
        v-else-if="step === 'quiz'" 
        @finished="handleQuizFinished" 
      />
      
      <QuizResults 
        v-else-if="step === 'results'" 
        :points="points" 
        :settings="settings" 
      />

      <ProjectSearch v-else-if="step === 'search'" />
    </main>
  </div>
</template>

<script>
import './assets/main.css'
import TheHeader from './components/UpBar.vue'
import QuizIntro from './components/QuizIntro.vue'
import QuizCard from './components/QuizCard.vue'
import QuizResults from './components/QuizResults.vue'
import ProjectSearch from './components/ProjectSearch.vue' // Import new component

export default {
  components: { 
    TheHeader, 
    QuizIntro, 
    QuizCard, 
    QuizResults, 
    ProjectSearch
  },
  data() {
    return {
      step: 'intro',
      points: {},
      settings: {}
    }
  },
  methods: {
    handleQuizFinished({ points, settings }) {
      this.points = points;
      this.settings = settings;
      this.step = 'results';
    },
    handleNavigation(target) {
      this.step = target;
      if (target === 'intro') { this.points = {}; }
    }
  }
}
</script>