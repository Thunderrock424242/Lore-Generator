"generate_books.py": """
import openai
import json
import os

# Configuration
API_KEY = os.getenv("OPENAI_API_KEY")  # Set your key as an environment variable
MODEL = "gpt-4"
NUM_BOOKS = 5
MAX_TOKENS = 600

# Your world description here or read from a file/config
world_description = \"\"\"
A magical apocalypse shattered the world. Survivors live in ruins. Strange glowing anomalies spread from deep caves.
\"\"\"

openai.api_key = API_KEY

prompt_template = (
    "Generate a list of {count} immersive short lore books for Minecraft. Each book should have:\\n"
    "- A title (string)\\n"
    "- An author name (string)\\n"
    "- 2-4 pages of text, each 200 characters max.\\n"
    "Set the tone based on this world description:\\n"
    "{desc}\\n\\n"
    "Return the result as a valid JSON array of objects with this format:\\n"
    "[{{\"title\": ..., \"author\": ..., \"pages\": [\"page1\", \"page2\", ...]}}]"
)

def generate_books():
    prompt = prompt_template.format(count=NUM_BOOKS, desc=world_description)

    response = openai.ChatCompletion.create(
        model=MODEL,
        messages=[{"role": "user", "content": prompt}],
        temperature=0.8,
        max_tokens=MAX_TOKENS
    )

    try:
        content = response.choices[0].message.content.strip()
        books = json.loads(content)
        with open("generated_books.json", "w", encoding="utf-8") as f:
            json.dump(books, f, indent=2, ensure_ascii=False)
        print("✅ Saved generated_books.json with", len(books), "entries.")
    except Exception as e:
        print("❌ Error parsing response:", e)
        print("Raw content:\\n", content)

if __name__ == "__main__":
    generate_books()
"""