import type { Poi } from "../types/Poi.ts"

const API_URL = "http://localhost:8080/api/pois"

// Get all POIs
export async function getPois(): Promise<Poi[]> {
    const res = await fetch(`${API_URL}/getAll`)

    if (!res.ok) {
        throw new Error("Failed to fetch POIs")
    }

    return res.json()
}

// Get POI by ID
export async function getPoiById(id: number): Promise<Poi> {
    const res = await fetch(`${API_URL}/get/${id}`)

    if (!res.ok) {
        throw new Error("POI not found")
    }

    return res.json()
}

// Creat POI
export async function createPoi(poi: Poi): Promise<any> {
    const res = await fetch(`${API_URL}/create`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(poi),
    })

    const data = await res.json()

    if (!res.ok) {
        throw data
    }

    return data
}

// Update POI
export async function updatePoi(id: number, poi: Poi): Promise<Poi> {
    const res = await fetch(`${API_URL}/update/${id}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(poi),
    })

    const data = await res.json()

    if (!res.ok) {
        throw data
    }

    return data
}

// Delete POI
export async function deletePoi(id: number): Promise<void> {
    const res = await fetch(`${API_URL}/delete/${id}`, {
        method: "DELETE",
    })

    if (!res.ok) {
        throw new Error("Failed to delete POI")
    }
}