<template>
  <div class="mt-10 max-w-4xl mx-auto">
    <!-- Show message if no POIs exist -->
    <p
        v-if="pois.length === 0"
        class="text-center text-gray-500 italic"
    >
      No POIs found
    </p>

    <!-- Display table of POIs if there are any -->
    <table
        v-else
        class="w-full border rounded-lg overflow-hidden bg-white"
    >
      <thead class="bg-gray-100">
      <tr>
        <th class="p-3 text-left font-medium">Name</th>
        <th class="p-3 text-left font-medium">Latitude</th>
        <th class="p-3 text-left font-medium">Longitude</th>
        <th class="p-3 text-left font-medium">Year</th>
        <th class="p-3 text-center font-medium">Actions</th>
      </tr>
      </thead>

      <tbody>
      <!-- Loop through all POIs and display each row -->
      <tr
          v-for="poi in pois"
          :key="poi.id"
          class="border-t hover:bg-gray-50"
      >
        <td class="p-3">{{ poi.name }}</td>
        <td class="p-3">{{ poi.latitude }}</td>
        <td class="p-3">{{ poi.longitude }}</td>
        <td class="p-3">{{ poi.constructionYear }}</td>
        <td class="p-3 text-center space-x-3">
          <button
              @click="$emit('edit', poi)"
              class="px-3 py-1 rounded bg-blue-100 text-blue-700 hover:bg-blue-200"
          >
            Edit
          </button>

          <button
              @click="$emit('delete', poi.id!)"
              class="px-3 py-1 rounded bg-red-600 text-white hover:bg-red-700"
          >
            Delete
          </button>
        </td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<script setup lang="ts">
import type { Poi } from "../types/Poi.ts"

// List of POIs to display in the table
defineProps<{ pois: Poi[] }>()

// Events for editing and deleting a POI
defineEmits<{
  (e: "edit", poi: Poi): void
  (e: "delete", id: number): void
}>()
</script>
