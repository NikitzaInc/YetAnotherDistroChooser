<template>
  <div class="results-container">
    <div v-if="loading" class="status-box">
      <div class="spinner"></div>
      <transition name="fade" mode="out-in">
        <p class="loading-text" :key="currentText">{{ currentText }}</p>
      </transition>
    </div>

    <div v-else-if="error" class="status-box error">
      <p>⚠️ {{ error }}</p>
      <button @click="fetchResults" class="btn-primary">Try Again</button>
    </div>

    <div v-else class="results-content">
      <h2>Your Recommendations</h2>
      <p class="summary">Based on your focus on <strong>{{ topPreference }}</strong>:</p>
      
      <div class="distro-list">
        <div v-for="(distro, index) in results" :key="index" class="distro-card">
          
          <div class="card-header">
            <div class="header-left">
              <img :src="distro.imageUrl" :alt="distro.name" class="distro-logo" />
              <div class="title-group">
                <h3>{{ distro.name }}</h3>
                <span class="status-badge" :class="distro.status.toLowerCase()">{{ distro.status }}</span>
              </div>
            </div>
            
            <div class="header-actions">
              <button 
                @click="sendFeedback(distro, index)" 
                class="btn-icon heart-btn"
                :class="{ 'is-liked': distro.liked }"
                title="Like this recommendation"
              >
                <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                  <path d="M20.84 4.61a5.5 5.5 0 0 0-7.78 0L12 5.67l-1.06-1.06a5.5 5.5 0 0 0-7.78 7.78l1.06 1.06L12 21.23l7.78-7.78 1.06-1.06a5.5 5.5 0 0 0 0-7.78z"></path>
                </svg>
              </button>

              <a :href="distro.homepage" target="_blank" class="btn-link">
                Visit Website
              </a>
              <button @click="toggleDetails(index)" class="btn-icon">
                {{ distro.expanded ? '▼' : '▶' }}
              </button>
            </div>
          </div>

          <transition name="fade">
            <div v-if="distro.expanded" class="card-details">
              <p class="description">{{ distro.description }}</p>
              
              <div class="details-grid">
                <div class="detail-item"><strong>Based On:</strong> {{ distro.basedOn }}</div>
                <div class="detail-item"><strong>Origin:</strong> {{ distro.origin }}</div>
                <div class="detail-item"><strong>Architecture:</strong> {{ distro.architectures.join(', ') }}</div>
                <div class="detail-item"><strong>Package Mgr:</strong> {{ distro.packageManagement }}</div>
                <div class="detail-item"><strong>Release:</strong> {{ distro.releaseDate }}</div>
                <div class="detail-item"><strong>Latest Ver:</strong> {{ distro.lastVersion }}</div>
                <div class="detail-item full-width"><strong>Categories:</strong> {{ distro.categories.join(', ') }}</div>
              </div>

              <div class="action-row">
                <a v-if="isValidLink(distro.distrowatchUrl)" :href="distro.distrowatchUrl" target="_blank" class="btn-secondary small">
                  DistroWatch
                </a>
                <a v-if="isValidLink(distro.documentation)" :href="distro.documentation" target="_blank" class="btn-secondary small">
                  Documentation
                </a>
                <a v-if="isValidLink(distro.userForum)" :href="distro.userForum" target="_blank" class="btn-secondary small">
                  Forum
                </a>
                <a v-if="isValidLink(distro.bugTracker)" :href="distro.bugTracker" target="_blank" class="btn-secondary small">
                  Bug Tracker
                </a>
              </div>
            </div>
          </transition>
        </div>
      </div>
      
      <button @click="restart" class="btn-restart">Take Quiz Again</button>
    </div>
  </div>
</template>

<script>
export default {
  props: ['points', 'settings'],
  data() {
    return {
      loading: false,
      error: null,
      results: null,
      // --- New Data Properties ---
      currentText: "Analyzing your preferences...", 
      loadingTimer: null,
      messages: [
        "Analyzing your preferences...",
        "Scanning DistroWatch database...",
        "Comparing package managers...",
        "Checking hardware compatibility...",
        "Finalizing recommendations..."
      ]
    };
  },
  computed: {
    topPreference() {
      return Object.keys(this.points).reduce((a, b) => this.points[a] > this.points[b] ? a : b);
    },
    searchParams() {
      let params = {
        ostype: 'Linux',
        category: this.settings.category || 'All',
        origin: 'All',
        basedon: this.settings.basedon || 'All',
        notbasedon: 'None',
        desktop: this.settings.desktop || 'All',
        architecture: 'x86_64',
        package: 'All',
        rolling: this.settings.rolling || 'All',
        isosize: this.settings.isosize || 'All',
        netinstall: this.settings.netinstall || 'All',
        language: 'All',
        defaultinit: 'All',
        status: 'Active'
      };

      // Apply dominant value overrides
      const maxValue = Math.max(...Object.values(this.points));
      if (maxValue >= 3) {
        if (this.points.Comfort === maxValue) {
          params.category = 'Beginners';
          params.rolling = 'Standard';
        } else if (this.points.Security === maxValue) {
          params.category = 'Security';
        } else if (this.points.Performance === maxValue) {
          params.category = 'Old Computers';
          params.isosize = '<2GB';
        } else if (this.points.Stability === maxValue) {
          params.rolling = 'Standard';
          params.basedon = 'Debian';
        } else if (this.points.Customization === maxValue) {
          params.basedon = 'Arch';
          params.rolling = 'Rolling';
        }
      }

      const queryString = Object.entries(params).map(([key, value]) => `${key}=${encodeURIComponent(value)}`).join('&');
      return `https://distrowatch.com/search.php?${queryString}#simpleresults`;
    }
  },
  mounted() {
    this.fetchResults();
  },
  beforeUnmount() {
    this.stopLoadingAnimation();
  },
  methods: {
    startLoadingAnimation() {
      let index = 0;
      this.loadingTimer = setInterval(() => {
        index = (index + 1) % this.messages.length;
        this.currentText = this.messages[index];
      }, 3000);
    },
    stopLoadingAnimation() {
      if (this.loadingTimer) {
        clearInterval(this.loadingTimer);
        this.loadingTimer = null;
      }
    },
    async fetchResults() {
      this.loading = true;
      this.error = null;
      this.startLoadingAnimation();
      const BACKEND_URL = process.env.VUE_APP_BACKEND_URL;

      const dw_url = this.searchParams;
      const params = new URLSearchParams();
      params.append("distroWatchLink", dw_url);

      try {
        const response = await fetch(`${BACKEND_URL}?${params.toString()}`, {
          method: 'GET',
          headers: { 'Accept': 'application/json' }
          });
        if (!response.ok) throw new Error('Failed to reach server');
        
        const rawData = await response.json();
        
        // Map data to include 'expanded' property for UI state and 'liked' property
        this.results = rawData.map(item => ({
          ...item,
          expanded: false,
          liked: false
        }));
        
      } catch (err) {
        this.error = err.message;
      } finally {
        this.loading = false;
      }
    },
    async sendFeedback(distro, index) {
      // Prevent multiple clicks
      if (distro.liked) return;

      // Optimistic UI update
      this.results[index].liked = true;
      this.$forceUpdate();

      const BACKEND_URL = process.env.VUE_APP_BACKEND_URL;
      // Remove trailing slash if present to avoid //feedback
      const baseUrl = BACKEND_URL.replace(/\/$/, ""); 

      try {
        await fetch(`${baseUrl}/feedback`, {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify({
            associatedLink: this.searchParams, // The generated Distrowatch link
            likedDistro: distro.name
          })
        });
      } catch (err) {
        console.error("Failed to send feedback:", err);
        // Revert UI if request fails
        this.results[index].liked = false;
        this.$forceUpdate();
      }
    },
    toggleDetails(index) {
      this.results[index].expanded = !this.results[index].expanded;
      this.$forceUpdate();
    },
    isValidLink(url) {
      return url && url !== '--' && url.startsWith('http');
    },
    restart() {
      window.location.reload();
    }
  }
};
</script>

<style scoped>
.results-container {
  background: white;
  padding: 2.5rem;
  border-radius: 12px;
  box-shadow: 0 4px 6px rgba(0,0,0,0.1);
  text-align: center;
  max-width: 800px;
  margin: 0 auto;
}

.status-box {
  padding: 3rem;
}

.spinner {
  width: 40px;
  height: 40px;
  border: 4px solid #f3f3f3;
  border-top: 4px solid #3b82f6; /* Primary blue */
  border-radius: 50%;
  animation: spin 1s linear infinite;
  margin: 0 auto 1rem;
}

@keyframes spin { 0% { transform: rotate(0deg); } 100% { transform: rotate(360deg); } }

.summary {
  color: #64748b;
  margin-bottom: 2rem;
}

/* Distro Card Styling */
.distro-list {
  display: flex;
  flex-direction: column;
  gap: 1rem;
  text-align: left;
}

.distro-card {
  border: 1px solid #e2e8f0;
  border-radius: 8px;
  overflow: hidden;
  transition: box-shadow 0.2s;
}

.distro-card:hover {
  box-shadow: 0 2px 8px rgba(0,0,0,0.05);
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 1rem;
  background: #f8fafc;
}

.header-left {
  display: flex;
  align-items: center;
  gap: 1rem;
}

.distro-logo {
  width: 48px;
  height: 48px;
  object-fit: contain;
}

.title-group h3 {
  margin: 0;
  font-size: 1.1rem;
  color: #1e293b;
}

.status-badge {
  font-size: 0.75rem;
  padding: 0.1rem 0.5rem;
  border-radius: 12px;
  background: #eee;
  color: #666;
}

.status-badge.active {
  background: #dcfce7;
  color: #166534;
}

.header-actions {
  display: flex;
  gap: 0.5rem;
  align-items: center;
}

/* Expanded Details Styling */
.card-details {
  padding: 1.5rem;
  border-top: 1px solid #e2e8f0;
  background: white;
}

.description {
  margin-bottom: 1.5rem;
  line-height: 1.6;
  color: #475569;
}

.details-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 0.8rem;
  margin-bottom: 1.5rem;
  font-size: 0.9rem;
}

.detail-item strong {
  color: #1e293b;
}

.full-width {
  grid-column: 1 / -1;
}

.action-row {
  display: flex;
  flex-wrap: wrap;
  gap: 0.5rem;
  padding-top: 1rem;
  border-top: 1px dashed #e2e8f0;
}

/* Buttons */
.btn-link {
  background: #3b82f6;
  color: white;
  text-decoration: none;
  padding: 0.5rem 1rem;
  border-radius: 6px;
  font-size: 0.9rem;
  transition: background 0.2s;
}

.btn-link:hover {
  background: #2563eb;
}

.btn-secondary {
  background: #f1f5f9;
  color: #475569;
  text-decoration: none;
  padding: 0.5rem 1rem;
  border-radius: 6px;
  border: 1px solid #cbd5e1;
  transition: all 0.2s;
}

.btn-secondary:hover {
  background: #e2e8f0;
  color: #1e293b;
}

.btn-secondary.small {
  font-size: 0.8rem;
  padding: 0.3rem 0.8rem;
}

.btn-icon {
  background: none;
  border: none;
  font-size: 1.2rem;
  cursor: pointer;
  color: #94a3b8;
  padding: 0 0.5rem;
  display: flex;
  align-items: center;
  justify-content: center;
}

.btn-icon:hover {
  color: #475569;
}

/* Heart Button Specifics */
.heart-btn svg {
  transition: all 0.2s ease;
}

.heart-btn:hover svg {
  transform: scale(1.1);
}

.heart-btn.is-liked {
  color: #ef4444; /* Red color */
  cursor: default;
}

.heart-btn.is-liked svg {
  fill: #ef4444;
}

.btn-restart {
  margin-top: 2rem;
  padding: 0.8rem 2rem;
  background: #e2e8f0;
  border: none;
  border-radius: 6px;
  cursor: pointer;
  font-weight: 600;
  color: #475569;
}

.btn-restart:hover {
  background: #cbd5e1;
}

/* Transitions */
.fade-enter-active, .fade-leave-active {
  transition: opacity 0.2s;
}
.fade-enter, .fade-leave-to {
  opacity: 0;
}
</style>