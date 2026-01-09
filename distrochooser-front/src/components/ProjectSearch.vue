<template>
  <div class="search-container">
    <div class="search-header">
      <h2>Distro Package Search</h2>
      <p>Check which distributions support your favorite project.</p>
    </div>

    <div class="search-controls">
      <input 
        v-model="query" 
        @keyup.enter="searchProject"
        placeholder="Enter project name (e.g. firefox, gimp)..." 
        class="search-input"
      />
      <button @click="searchProject" class="search-btn" :disabled="loading">
        {{ loading ? 'Searching...' : 'Search' }}
      </button>
    </div>

    <div class="filters" v-if="results.length > 0">
      <input 
        v-model="repoFilter" 
        placeholder="Filter results by distro/repo name..." 
        class="local-filter-input"
      />
      
      <label class="checkbox-label">
        <input type="checkbox" v-model="onlyLatest">
        <span>Show only latest versions</span>
      </label>
    </div>

    <div v-if="error" class="error-msg">{{ error }}</div>

    <div v-if="results.length > 0" class="results-table-wrapper">
      <table class="results-table">
        <thead>
          <tr>
            <th>Distribution / Repo</th>
            <th>Version</th>
            <th>Status</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(pkg, index) in filteredResults" :key="index" :class="{'is-latest': pkg.status === 'newest'}">
            <td class="repo-name">{{ pkg.repo }}</td>
            <td class="version-tag">{{ pkg.version }}</td>
            <td>
              <span :class="['status-badge', pkg.status]">
                {{ pkg.status || 'unknown' }}
              </span>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    
    <div v-else-if="hasSearched && !loading" class="no-results">
      No packages found. Try a different project name.
    </div>
  </div>
</template>

<script>
export default {
  name: 'ProjectSearch',
  data() {
    return {
      query: '',
      repoFilter: '',
      results: [],
      loading: false,
      error: null,
      hasSearched: false,
      onlyLatest: false
    }
  },
  computed: {
    filteredResults() {
      let filtered = this.results;

      if (this.onlyLatest) {
        filtered = filtered.filter(pkg => pkg.status === 'newest');
      }

      if (this.repoFilter.trim()) {
        const term = this.repoFilter.toLowerCase();
        filtered = filtered.filter(pkg => 
          pkg.repo.toLowerCase().includes(term)
        );
      }

      return filtered;
    }
  },
  methods: {
    async searchProject() {
      if (!this.query.trim()) return;
      
      this.loading = true;
      this.error = null;
      this.results = [];
      this.hasSearched = true;
      this.repoFilter = '';

      try {
        const response = await fetch(`/api-repology/api/v1/project/${this.query.toLowerCase()}`);
        
        if (!response.ok) {
          if (response.status === 404) throw new Error('Project not found');
          throw new Error('Failed to fetch data');
        }

        const data = await response.json();
        this.results = data.filter((item, index, self) =>
          index === self.findIndex((t) => (
            t.repo === item.repo && 
            t.version === item.version && 
            t.status === item.status
          ))
        );

      } catch (err) {
        this.error = err.message;
      } finally {
        this.loading = false;
      }
    }
  }
}
</script>

<style scoped>
.search-container {
  max-width: 800px;
  margin: 40px auto;
  padding: 0 20px;
}

.search-header {
  text-align: center;
  margin-bottom: 30px;
}

.search-controls {
  display: flex;
  gap: 10px;
  margin-bottom: 20px;
}

.search-input {
  flex: 1;
  padding: 12px;
  border: 2px solid #ddd;
  border-radius: 6px;
  font-size: 1rem;
}

.search-btn {
  padding: 12px 24px;
  background-color: var(--secondary, #333);
  color: white;
  border: none;
  border-radius: 6px;
  cursor: pointer;
  font-weight: bold;
}

.search-btn:disabled {
  opacity: 0.7;
  cursor: not-allowed;
}

.filters {
  margin-bottom: 20px;
  padding: 12px;
  background: #f5f5f5;
  border-radius: 6px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 20px;
}

.local-filter-input {
  flex: 1;
  max-width: 300px;
  padding: 8px 12px;
  border: 1px solid #ccc;
  border-radius: 4px;
  font-size: 0.9rem;
}

.checkbox-label {
  display: flex;
  align-items: center;
  gap: 8px;
  cursor: pointer;
  font-weight: 500;
  white-space: nowrap;
}

.results-table-wrapper {
  overflow-x: auto;
  box-shadow: 0 4px 6px rgba(0,0,0,0.1);
  border-radius: 8px;
}

.results-table {
  width: 100%;
  border-collapse: collapse;
  background: white;
}

.results-table th, .results-table td {
  padding: 12px 15px;
  text-align: left;
  border-bottom: 1px solid #eee;
}

.results-table th {
  background-color: #f8f9fa;
  font-weight: 600;
}

.repo-name {
  font-weight: 500;
  color: #2c3e50;
}

.version-tag {
  font-family: monospace;
  font-size: 1.1em;
}

.status-badge {
  padding: 4px 8px;
  border-radius: 12px;
  font-size: 0.85rem;
  text-transform: capitalize;
  background: #eee;
}

.status-badge.newest {
  background-color: #d4edda;
  color: #155724;
}

.status-badge.outdated {
  background-color: #f8d7da;
  color: #721c24;
}

.error-msg {
  color: #dc3545;
  text-align: center;
  margin-top: 20px;
}

.no-results {
  text-align: center;
  color: #666;
  margin-top: 20px;
}
</style>