<template>
  <!-- Main container for the POI page -->
  <div class="min-h-screen bg-gray-100 flex flex-col justify-center p-8">
    <h1 class="text-2xl font-bold text-center mb-8">
      Points of Interest
    </h1>

    <!-- POI Form component -->
    <PoiForm
        :modelValue="selected"
        :errors="errors"
        @save="save"
    />

    <!-- POI List component -->
    <PoiList
        :pois="pois"
        @edit="selected = $event"
        @delete="remove"
    />
  </div>
</template>

<script setup lang="ts">
import {ref, onMounted} from "vue"
import PoiForm from "../components/PoiForm.vue"
import PoiList from "../components/PoiList.vue"
import {getPois, createPoi, updatePoi, deletePoi} from "../api/PoiApi.ts"
import type {Poi} from "../types/Poi.ts"

// Store list of POIs
const pois = ref<Poi[]>([])

// Store currently selected POI for editing (for create or update)
const selected = ref<Poi>({
  name: "",
  latitude: 0,
  longitude: 0,
  constructionYear: 1
})

// Store backend validation errors
const errors = ref<Record<string, string>>({})

// Load POIs from the API
async function load() {
  pois.value = await getPois()
}

// Save or update a POI
async function save(poi: Poi) {
  // Clear errors
  errors.value = {}

  try {
    if (poi.id) {
      // If POI has an ID, update existing POI
      await updatePoi(poi.id, poi)
    } else {
      // Otherwise, create a new POI
      await createPoi(poi)
    }

    // Reset the selected POI form
    selected.value = {name: "", latitude: 0, longitude: 0, constructionYear: 1}

    // Reload the POI list
    await load()
  } catch (err: any) {
    // Handle validation errors from the backend
    const response = await err?.json?.()
    if (response) {
      errors.value = response
    }
  }
}

// Delete a POI
async function remove(id: number) {
  await deletePoi(id)
  await load()
}

// Load all POIs when component is mounted
onMounted(load)
</script>