import axios from "axios";
import type Partie from "@/types/Partie";

const baseURL = 'http://localhost:8080/api/klammern';
const developmentBaseURL = 'backend/klammern';

const apiClient = axios.create({
        baseURL: import.meta.env.DEV ? developmentBaseURL : baseURL,
        withCredentials: false,
        headers: {
            Accept: "application/json",
            "Content-Type": "application/json",
        }
    }
);


export default async function fetchPartie(): Promise<Partie> {
    console.log("fetchPartie ist aufgerufen")
    const response = await apiClient.get<Partie>('/partie')
    console.log(response.data)
    return response.data;
}