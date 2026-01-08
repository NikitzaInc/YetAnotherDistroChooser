<template>
  <header class="main-header">
    <div class="header-content">
      <a href="/" class="logo-link">
        <div class="logo-container">
          <img src="../assets/logo-nobg.png" alt="YADC Logo" class="logo-img" />
          <span class="logo-text">YetAnotherDistroChooser</span>
        </div>
      </a>

      <nav class="resources-container">
        <span class="resources-label">Useful Resources:</span>
        
        <a href="https://distrowatch.com/" target="_blank" rel="noopener noreferrer" class="resource-item">
          <img src="../assets/links/db.svg" alt="DB Icon" class="link-icon" />
          <span>
            <div>DistroWatch</div>
            <div>(Global database)</div>
          </span>
        </a>

        <a href="https://tuxmate.com/" target="_blank" rel="noopener noreferrer" class="resource-item">
          <img src="../assets/links/download.svg" alt="Download Icon" class="link-icon" />
          <span>
            <div>TuxMate</div>
            <div>(Bulk app installer)</div>
          </span>
        </a>

        <a href="https://alternativeto.net/platform/linux/" target="_blank" rel="noopener noreferrer" class="resource-item">
          <img src="../assets/links/switch.svg" alt="Switch Icon" class="link-icon" />
          <span>
            <div>AlternativeTo</div>
            <div>(Software)</div>
          </span>
        </a>
      </nav>
    </div>
  </header>
</template>

<style scoped>
.main-header {
  width: 100%;
  background-color: var(--secondary);
  box-shadow: var(--shadow);
  padding: 10px 0;
}

.header-content {
  max-width: 10000px;
  margin: 0 auto;
  padding: 0 20px;
  display: flex;
  justify-content: space-between; 
  align-items: center; 
}

.logo-link {
  text-decoration: none;
  color: inherit;
  transition: opacity 0.2s;
}
.logo-link:hover {
  opacity: 0.8;
}
.logo-container {
  display: flex;
  align-items: center;
  gap: 12px;
}
.logo-img {
  height: 80px;
  width: auto;
  display: block;
}
.logo-text {
  font-weight: bold;
  font-size: 1.2rem;
}

.resources-container {
  display: flex;
  align-items: center;
  gap: 20px;
}

.resources-label {
  font-weight: 600;
  opacity: 0.8;
  margin-right: 5px;
  display: none;
}

@media (min-width: 850px) {
  .resources-label {
    display: inline-block;
  }
}

.resource-item {
  display: flex;
  align-items: center;
  gap: 8px;
  text-decoration: none;
  color: var(--text-color, #333);
  font-weight: 500;
  font-size: 0.95rem;
  padding: 6px 16px;
  border-radius: 6px;
  transition: background-color 0.2s ease, transform 0.1s;
}

.resource-item:hover {
  background-color: rgba(255, 255, 255, 0.1);
  transform: translateY(-1px);
}

.link-icon {
  width: 20px;
  height: 20px;
  object-fit: contain;
  /* Optional: if your SVGs are black and you need them white, you might need filter: invert(1); here depending on theme */
}
</style>