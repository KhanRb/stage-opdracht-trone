<template>
  <form
      @submit.prevent="submit"
      class="bg-white p-6 rounded-lg shadow-md max-w-md min-w-md mx-auto space-y-6"
  >
    <!-- Form title: switches between 'Create' and 'Edit' -->
    <h2 class="text-lg font-semibold text-center">
      {{ form.id ? "Edit POI" : "Create POI" }}
    </h2>

    <!-- Name field -->
    <div>
      <label class="block font-medium mb-1">
        Name <span class="text-red-500">*</span>
      </label>
      <input
          v-model="form.name"
          :class="[
          'w-full border rounded px-3 py-2',
          submitted && errors.name
            ? 'border-red-500 focus:ring-red-500'
            : 'focus:ring-2 focus:ring-blue-500'
        ]"
      />
      <!-- Show validation error if submitted and invalid -->
      <p v-if="submitted && errors.name" class="text-red-600 text-sm mt-1">
        {{ errors.name }}
      </p>
    </div>

    <!-- Latitude field -->
    <div>
      <label class="block font-medium mb-1">
        Latitude <span class="text-red-500">*</span>
      </label>
      <input
          v-model.number="form.latitude"
          type="number"
          :class="[
          'w-full border rounded px-3 py-2',
          submitted && errors.latitude
            ? 'border-red-500 focus:ring-red-500'
            : 'focus:ring-2 focus:ring-blue-500'
        ]"
      />
      <p v-if="submitted && errors.latitude" class="text-red-600 text-sm mt-1">
        {{ errors.latitude }}
      </p>
    </div>

    <!-- Longitude field -->
    <div>
      <label class="block font-medium mb-1">
        Longitude <span class="text-red-500">*</span>
      </label>
      <input
          v-model.number="form.longitude"
          type="number"
          :class="[
          'w-full border rounded px-3 py-2',
          submitted && errors.longitude
            ? 'border-red-500 focus:ring-red-500'
            : 'focus:ring-2 focus:ring-blue-500'
        ]"
      />
      <p v-if="submitted && errors.longitude" class="text-red-600 text-sm mt-1">
        {{ errors.longitude }}
      </p>
    </div>

    <!-- Construction Year field  -->
    <div>
      <label class="block font-medium mb-1">Construction Year</label>
      <input
          v-model.number="form.constructionYear"
          type="number"
          :class="[
          'w-full border rounded px-3 py-2',
          submitted && errors.constructionYear
            ? 'border-red-500 focus:ring-red-500'
            : 'focus:ring-2 focus:ring-blue-500'
        ]"
      />
      <p
          v-if="submitted && errors.constructionYear"
          class="text-red-600 text-sm mt-1"
      >
        {{ errors.constructionYear }}
      </p>
    </div>

    <button
        class="mt-6 w-full bg-blue-600 text-white py-2 rounded-lg hover:bg-blue-700 transition"
    >
      Save POI
    </button>
  </form>
</template>

<script setup lang="ts">
import { ref, watch } from "vue"
import type { Poi } from "../types/Poi"

// current POI model
const props = defineProps<{
  modelValue: Poi
  serverErrors?: Record<string, string>
}>()

const emit = defineEmits<{
  (e: "save", poi: Poi): void
}>()

const form = ref<Poi>({ ...props.modelValue })
const submitted = ref(false)
const errors = ref<Record<string, string>>({})

// Watch for changes in the modelValue
watch(
    () => props.modelValue,
    (val) => {
      form.value = { ...val }
      submitted.value = false
      errors.value = {}
    }
)

// Validate form fields
function validate() {
  errors.value = {}

  if (!form.value.name?.trim()) {
    errors.value.name = "Name is required."
  }

  if (form.value.latitude === "") {
    errors.value.latitude = "Latitude is required."
  }

  if (form.value.longitude === "") {
    errors.value.longitude = "Longitude is required."
  }

  if (
      form.value.constructionYear !== null &&
      form.value.constructionYear < 1
  ) {
    errors.value.constructionYear = "Construction year must be greater than 0."
  }

  return Object.keys(errors.value).length === 0
}

// Handle form submission
function submit() {
  submitted.value = true

  if (!validate()) {
    return
  }

  emit("save", form.value)
}
</script>
