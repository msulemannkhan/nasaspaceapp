from random import randrange
from app.config import settings


def get_ml_model_key(key_index: int = None) -> str:
    ML_MODELS_KEYS = [
        settings.ML_MODEL_KEY_1,
        settings.ML_MODEL_KEY_2,
        settings.ML_MODEL_KEY_3,
        settings.ML_MODEL_KEY_4,
        settings.ML_MODEL_KEY_5,
        settings.ML_MODEL_KEY_6,
        settings.ML_MODEL_KEY_7,
        settings.ML_MODEL_KEY_8
    ]

    if key_index and key_index < len(ML_MODELS_KEYS) and key_index > 0:
        return ML_MODELS_KEYS[key_index]
    
    return ML_MODELS_KEYS[randrange(len(ML_MODELS_KEYS))]