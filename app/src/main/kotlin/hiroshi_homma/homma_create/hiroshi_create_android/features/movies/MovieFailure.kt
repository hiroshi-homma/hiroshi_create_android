package hiroshi_homma.homma_create.hiroshi_create_android.features.movies

import hiroshi_homma.homma_create.hiroshi_create_android.core.exception.Failure.FeatureFailure

class MovieFailure {
    class ListNotAvailable: FeatureFailure()
    class NonExistentMovie: FeatureFailure()
}

