<script setup>
import { ref } from 'vue'

const longitude = ref(-2.0115245)
const latitude = ref(52.00514984)
const distance = ref(100)
const speciality = ref('pharmacology')

const hospitals = ref([])
const loading = ref(false)
const error = ref('')

async function searchHospitals() {
  loading.value = true
  error.value = ''
  hospitals.value = []

  try {
    const url =
      `/api/nearest/${longitude.value}/${latitude.value}/${distance.value}/${encodeURIComponent(speciality.value)}`

    const response = await fetch(url)

    if (!response.ok) {
      throw new Error(
        response.status === 404
          ? 'Aucun hôpital correspondant n’a été trouvé.'
          : 'Une erreur est survenue lors de la recherche.'
      )
    }

    hospitals.value = await response.json()
  } catch (err) {
    error.value = err.message
  } finally {
    loading.value = false
  }
}

function formatDuration(seconds) {
  const minutes = Math.round(seconds / 60)

  if (minutes < 60) {
    return `${minutes} min`
  }

  const hours = Math.floor(minutes / 60)
  const remainingMinutes = minutes % 60

  return `${hours} h ${remainingMinutes} min`
}
</script>

<template>
  <main class="app">
    <header class="header">
      <div>
        <p class="eyebrow">MedHead Consortium</p>
        <h1>Hospital Finder</h1>
        <p class="subtitle">
          Recherche de l’établissement hospitalier disponible le plus adapté
          à une intervention d’urgence.
        </p>
      </div>
    </header>

    <section class="search-card">
      <h2>Rechercher un hôpital</h2>

      <form class="search-form" @submit.prevent="searchHospitals">
        <div class="field">
          <label for="longitude">Longitude</label>
          <input
            id="longitude"
            v-model.number="longitude"
            type="number"
            step="any"
            required
          />
        </div>

        <div class="field">
          <label for="latitude">Latitude</label>
          <input
            id="latitude"
            v-model.number="latitude"
            type="number"
            step="any"
            required
          />
        </div>

        <div class="field">
          <label for="distance">Rayon de recherche (km)</label>
          <input
            id="distance"
            v-model.number="distance"
            type="number"
            min="1"
            required
          />
        </div>

        <div class="field">
          <label for="speciality">Spécialité médicale</label>
          <input
            id="speciality"
            v-model.trim="speciality"
            type="text"
            placeholder="Ex. pharmacology"
            required
          />
        </div>

        <button type="submit" :disabled="loading">
          {{ loading ? 'Recherche en cours...' : 'Rechercher' }}
        </button>
      </form>
    </section>

    <p v-if="error" class="error-message">
      {{ error }}
    </p>

    <section v-if="hospitals.length" class="results">
      <div class="results-header">
        <div>
          <p class="eyebrow">Résultats</p>
          <h2>Hôpitaux recommandés</h2>
        </div>

        <span class="result-count">
          {{ hospitals.length }} établissement{{ hospitals.length > 1 ? 's' : '' }}
        </span>
      </div>

      <article
        v-for="(route, index) in hospitals"
        :key="route.hopitalDestination.id"
        class="hospital-card"
      >
        <div class="rank">
          {{ index + 1 }}
        </div>

        <div class="hospital-content">
          <div class="hospital-heading">
            <div>
              <h3>{{ route.hopitalDestination.name }}</h3>
              <p>{{ route.hopitalDestination.address }}</p>
            </div>

            <span class="beds">
              {{ route.hopitalDestination.freebed }} lits disponibles
            </span>
          </div>

          <div class="metrics">
            <div>
              <span class="metric-label">Distance routière</span>
              <strong>{{ Number(route.distanceGoal).toFixed(1) }} km</strong>
            </div>

            <div>
              <span class="metric-label">Temps estimé</span>
              <strong>{{ formatDuration(route.durationDrive) }}</strong>
            </div>
          </div>
        </div>
      </article>
    </section>
  </main>
</template>
